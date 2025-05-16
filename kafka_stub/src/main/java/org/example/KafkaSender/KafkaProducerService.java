package org.example.KafkaSender;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {


    KafkaTemplate<String, String> kafkaTemplate;


    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    // skip explicit topic declaration and grabbing them from properties
    // use them only inside rest controller while passing to method to send
    // and only here facilitate it as a variable


    public void sendToKafka(String topic, String message, String kafka_key_message, String traceId_plate, LocalDateTime initiation_time,List<RecordHeader> header_list) {
        try {
            LocalDateTime end_time = LocalDateTime.now();
            ProducerRecord<String, String> record = new ProducerRecord<>(topic,kafka_key_message,message);
            for (RecordHeader header : header_list) {
                record.headers().add(header);
            }
            kafkaTemplate.send(record);
            long duration = Duration.between(initiation_time,end_time).toMillis();
            log.info("{} Topic: {} Message:{} Delay:{}",
                    traceId_plate,
                    topic,
                    message.substring(0, 120),
                    duration);
        } catch (Exception excp) {
            log.error("{} Topic: {} Error: {} Message:{}",
                    traceId_plate,
                    topic,
                    excp,
                    message);
        }

    }



}
