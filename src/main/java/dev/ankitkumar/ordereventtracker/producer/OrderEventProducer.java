package dev.ankitkumar.ordereventtracker.producer;

import dev.ankitkumar.ordereventtracker.dto.OrderEvent;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEventProducer {
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderEvent(OrderEvent orderPlacedEvent) {

        try {
            kafkaTemplate.send("order-event", String.valueOf(orderPlacedEvent.orderId()), orderPlacedEvent);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
