package com.kynsoft.notification.infrastructure.service.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.kafka.entity.FileKafka;
import com.kynsof.share.core.infrastructure.util.CustomMultipartFile;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.infrastructure.service.AmazonClient;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConsumerSaveFileEventService {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private IAFileService fileService;

    // Ejemplo de un método listener
    @KafkaListener(topics = "file", groupId = "file")
    public void listen(String event) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            FileKafka eventRead = objectMapper.treeToValue(rootNode.get("data"), FileKafka.class);

            MultipartFile file = new CustomMultipartFile(eventRead.getFile(), eventRead.getFileName());
            try {
                String fileUrl = amazonClient.save(file, eventRead.getFileName());
                this.fileService.create(new AFileDto(eventRead.getId(), eventRead.getFileName(), eventRead.getMicroServiceName(), fileUrl));
            } catch (IOException ex) {
                Logger.getLogger(ConsumerSaveFileEventService.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConsumerSaveFileEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
