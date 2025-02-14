package com.kynsof.share.core.domain.kafka.producer.s3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.kafka.entity.FileKafka;
import com.kynsof.share.core.domain.kafka.event.CreateEvent;
import com.kynsof.share.core.domain.kafka.event.EventType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProducerDeleteFileEventService {
    private final KafkaTemplate<String, String> producer;

    public ProducerDeleteFileEventService(KafkaTemplate<String, String> producer) {
        this.producer = producer;
    }

    public void delete(FileKafka entity) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(new CreateEvent<>(entity, EventType.DELETED));

            this.producer.send("file", json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ProducerDeleteFileEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}