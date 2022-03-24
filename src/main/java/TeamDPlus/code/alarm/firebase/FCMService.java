package TeamDPlus.code.alarm.firebase;

import TeamDPlus.code.dto.request.NotificationRequestDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    // 맵형식으로 닉네임, 토큰 저장
    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void send(final NotificationRequestDto notificationRequest) throws InterruptedException, ExecutionException {
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationRequest.getTitle(),
                                notificationRequest.getMessage()))
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        logger.info("메세지 발송: " + response);
    }
}
