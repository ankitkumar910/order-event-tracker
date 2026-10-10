package dev.ankitkumar.ordereventtracker.listener;

import dev.ankitkumar.ordereventtracker.dto.OrderEvent;
import dev.ankitkumar.ordereventtracker.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderEventListener {
    private OrderService orderService;

    @KafkaListener(topics = "order-event", groupId = "do-next-phase-group")
    public void listenOrderEvent(OrderEvent orderEvent) throws InterruptedException {
        long orderId = orderEvent.orderId();

        System.out.println(
                "Received: orderId=" + orderEvent.orderId()
                        + ", status=" + orderEvent.status()
        );

        switch (orderEvent.status()) {
            case PLACED -> orderService.confirmOrder(orderId);
            case CONFIRMED -> orderService.shipOrder(orderId);
            case SHIPPED -> orderService.outForDelivery(orderId);
            case OUT_FOR_DELIVERY -> orderService.deliverOrder(orderId);
            case ATTEMPTED_DELIVERY -> orderService.contactCustomer(orderId);
        }
    }


    @KafkaListener(topics = "order-event", groupId = "notification-group")
    public void listenOrderEventAndNotifyUser(OrderEvent orderEvent) {

        String message;
        switch (orderEvent.status()) {
            case PLACED -> message = "Order is placed.";
            case CONFIRMED -> message = "Order is confirmed.";
            case SHIPPED -> message = "Order is shipped.";
            case OUT_FOR_DELIVERY -> message = "Order is out for delivery.";
            case ATTEMPTED_DELIVERY -> message = "Sorry! We tried to reach you. :(";
            default -> message = "No messages";
        }


        log.info("UserNotification: {}", message);

    }

    @KafkaListener(
            topics = "order-event.DLT",
            groupId = "dlt-handler-group"
    )
    public void listenDlt(OrderEvent orderEvent){
        log.warn("✉️ Dlt message: orderId = {}", orderEvent.itemId());
    }
}
