package TeamDPlus.code.alarm.firebase;

import TeamDPlus.code.dto.fcm.FCMMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FCMService {

    // redisTemplate 가져오기
    private final StringRedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);
    private static final String FIREBASE_CONFIG_PATH = "dplus-6acf6-firebase-adminsdk.json";
    private static final String api = "https://fcm.googleapis.com/v1/projects/dplus-6acf6/messages:send";
    private final ObjectMapper mapper;


    // 맵형식으로 닉네임, 토큰 저장 > hashmap 생성
    private final Map<String, String> tokenMap = new HashMap<>();
    private final Map<String, String> messageMap = new HashMap<>();

    // 토큰 저장 (username - token으로 세팅)
    public void saveToken(final String username, final String token){
        redisTemplate.opsForValue().set(username, token);
    }

    public String getToken(String username){
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteToken(final String username){
        redisTemplate.delete(username);
    }

    public boolean hasKey(String username){
        return redisTemplate.hasKey(username);
    }

    // 토큰 액세스 > 토큰 값 가져오기
    private static String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(FIREBASE_CONFIG_PATH))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    // notificationReqest에 notification을 받을 상대의 토큰, 메세지, 제목. 이미지
    public void send(String username, String title, String msg) throws InterruptedException,
            ExecutionException, JsonProcessingException {
        System.out.println("메세지 요청");

        // 싱글 디바이스나 디바이스 그룹 타겟팅하려면 토큰으로 엑세스 필요
        String targetToken = getToken(username);
        messageMap.put("admin", "admin");
        String  messageSending = makeMessage(targetToken, title, msg);

        // message로 빌드 (토큰 + 헤더, 데이터,
        Message message = Message.builder()
                .setToken(targetToken)
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300").putAllData(messageMap).build())
                .setNotification(new Notification(title, msg))
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        logger.info("메세지 발송: " + response);
    }

    private String makeMessage(String targetToken, String title, String msg) throws JsonProcessingException {
        FCMMessage fcmMessage = FCMMessage.builder()
                .msg(FCMMessage.Message.builder().
                        token(targetToken).notification(
                                FCMMessage.Notification.builder()
                                        .title(title)
                                        .message(msg)
                                        .img(null)
                                        .build()

                        ).build()
                ).validation(false)
                .build();
        return mapper.writeValueAsString(fcmMessage);
    }
}
