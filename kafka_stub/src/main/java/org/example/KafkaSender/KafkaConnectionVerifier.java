package org.example.KafkaSender;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Configuration
public class KafkaConnectionVerifier {

    @Value("custom.kafka_topic_1")
    private String TOPIC;

    @Bean
    public ApplicationRunner validateKafkaConnection(KafkaTemplate<String,String> kafkaTemplate){
        return args -> {
            try {
                kafkaTemplate.getProducerFactory().createProducer().partitionsFor(TOPIC);
                log.info("[KAFKA] connection established successfully!");
            } catch (Exception e) {
                log.error("[KAFKA] Failed to connect. Error: {}", e.toString());
            }


        };
    }

}
