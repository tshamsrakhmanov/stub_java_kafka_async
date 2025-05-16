package org.example.KafkaSender;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableAsync
@Service
@RequiredArgsConstructor
public class KafkaAsyncSender {


    private final KafkaProducerService kafkaProducerService;

    @Value("custom.sleep_before_message_send_to_kafka")
    private String DELAY;

    @Async
    public void sendAsyncKafkaMessage(String topic,
                                      String message,
                                      String kafka_key_message,
                                      String traceId_plate,
                                      LocalDateTime initiation_time,
                                      List<RecordHeader> header_list) throws InterruptedException {

        TimeUnit.MILLISECONDS.sleep(Long.parseLong(DELAY));
        kafkaProducerService.sendToKafka(topic, message,kafka_key_message,traceId_plate,initiation_time,header_list);
    }

}