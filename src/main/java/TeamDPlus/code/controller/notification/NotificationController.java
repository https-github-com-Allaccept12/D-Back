package TeamDPlus.code.controller.notification;

import TeamDPlus.code.alarm.firebase.FCMService;
import TeamDPlus.code.dto.fcm.FCMToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FCMService fcmService;

}
