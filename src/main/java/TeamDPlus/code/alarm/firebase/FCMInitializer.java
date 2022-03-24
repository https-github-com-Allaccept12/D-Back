package TeamDPlus.code.alarm.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FCMInitializer {

    // 어플 시작 시 firebase에 앱등록
    private static final Logger logger = LoggerFactory.getLogger(FCMInitializer.class);
    private static final String FIREBASE_CONFIG_PATH = "dplus-6acf6-firebase-adminsdk.json";

    // Bean Object 생성 후 DI작업까지 완료
    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    // 비밀키 셋팅
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource
                            (FIREBASE_CONFIG_PATH).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
