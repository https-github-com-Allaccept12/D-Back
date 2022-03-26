package com.example.dplus.controller.notification;

import com.example.dplus.alarm.firebase.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FCMService fcmService;

}
