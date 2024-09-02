package com.kynsoft.notification.controller;

import com.kynsoft.notification.application.command.sendFirebase.FCMNotificationService;
import com.kynsoft.notification.application.command.sendFirebase.NotificationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {
private final FCMNotificationService fcmNotificationService;

    @Autowired
    public FirebaseController(FCMNotificationService fcmNotificationService) {

        this.fcmNotificationService = fcmNotificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> uploadFile(@RequestBody NotificationRequestDto request) {
        try {
          fcmNotificationService.sendPnsToDevice(request);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send notification: " + e.getMessage());
        }
    }

}


