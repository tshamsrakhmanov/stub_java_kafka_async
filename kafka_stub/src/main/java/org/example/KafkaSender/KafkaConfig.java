package org.example.KafkaSender;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("spring.kafka.bootstrap-servers")
    private String bootstrapSevers;

    @Value("spring.kafka.ssl.trust-store-location")
    private Resource trustStoreLocation;

    @Value("spring.kafka.ssl.trust-store-password")
    private String trustStorePassword;

    @Value("spring.kafka.ssl.key-store-location")
    private Resource keystoreLocation;

    @Value("spring.kafka.ssl.key-store-password")
    private String keyStorePassword;


    public ProducerFactory<String, String> producerFactory() throws IOException{

        Map<String, Object> configProps = new HashMap<>();


        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapSevers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SSL");
        configProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
                trustStoreLocation.getFile().getAbsolutePath());
        configProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,
                keystoreLocation.getFile().getAbsolutePath());
        configProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,
                trustStorePassword);
        configProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG,
                keyStorePassword);

        return new DefaultKafkaProducerFactory<>(configProps);

    }

}
