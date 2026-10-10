package dev.ankitkumar.ordereventtracker.listener;

import dev.ankitkumar.ordereventtracker.dto.OrderEvent;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, OrderEvent> kafkaTemplate){
        System.out.println("I'm called. 🤖");

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record,exception)->
                        new TopicPartition(record.topic()+".DLT",record.partition()));

        return new DefaultErrorHandler(recoverer,new FixedBackOff(2000,3L));
    }
}
