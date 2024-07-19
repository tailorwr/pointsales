package com.kynsoft.socket.controller;

import com.kynsoft.socket.messages.LocalServiceMessage;
import com.kynsoft.socket.messages.NewServiceMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final SimpMessageSendingOperations messagingTemplate;

    public NotificationController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/turnero")
    public ResponseEntity<?> queueTurnero(@RequestBody NewServiceMessage request) {
        messagingTemplate.convertAndSend("/queue/turnero" + request.getQueueId(), request);
        System.out.println("Notificación enviada al turnero." + request.getQueueId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/local")
    public ResponseEntity<?> queueService(@RequestBody LocalServiceMessage request) {
        messagingTemplate.convertAndSend("/queue/local" + request.getQueueId(), request);
        System.out.println("Notificación enviada al local." + request.getQueueId());
        return ResponseEntity.ok(true);
    }
}
