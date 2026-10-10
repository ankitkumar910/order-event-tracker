package dev.ankitkumar.ordereventtracker.service;


import dev.ankitkumar.ordereventtracker.dto.OrderEvent;
import dev.ankitkumar.ordereventtracker.dto.OrderStatusResponseDto;
import dev.ankitkumar.ordereventtracker.entity.Order;
import dev.ankitkumar.ordereventtracker.entity.OrderStatus;
import dev.ankitkumar.ordereventtracker.entity.Status;
import dev.ankitkumar.ordereventtracker.producer.OrderEventProducer;
import dev.ankitkumar.ordereventtracker.repository.OrderRepository;
import dev.ankitkumar.ordereventtracker.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderStatusRepository orderStatusRepository;
    private OrderEventProducer orderEventProducer;

    public OrderStatusResponseDto createOrder(long itemId) {

        Order order = new Order();
        order.setItemId(itemId);
        Order savedOrder = orderRepository.save(order);

        return placeOrder(savedOrder);
    }

    private OrderStatusResponseDto placeOrder(Order savedOrder) {
        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrder(savedOrder);
        orderStatus.setStatus(Status.PLACED);
        orderStatusRepository.save(orderStatus);

        orderEventProducer.publishOrderEvent(new OrderEvent(savedOrder.getItemId(), savedOrder.getId(), Status.PLACED));

        return OrderStatusResponseDto.builder().message("Order received.").order_id(savedOrder.getId()).status(Status.PLACED).build();

    }

    public void confirmOrder(long orderId)
            throws InterruptedException {



        OrderEvent orderEvent = updateOrderStatus(orderId, Status.CONFIRMED);
        // Simulate Exception: Exception while confirming order.
           if(orderEvent.itemId() == 11)  throw new RuntimeException("Sorry! We could not confirm your order.Order I'd = "+orderId);

        orderEventProducer.publishOrderEvent(orderEvent);

    }



    public void shipOrder(long orderId)
            throws InterruptedException {
        OrderEvent orderEvent = updateOrderStatus(orderId, Status.SHIPPED);
        orderEventProducer.publishOrderEvent(orderEvent);
    }


    public void outForDelivery(long orderId)
            throws InterruptedException {
        OrderEvent orderEvent = updateOrderStatus(orderId, Status.OUT_FOR_DELIVERY);
        orderEventProducer.publishOrderEvent(orderEvent);
    }

    public void deliverOrder(long orderId)
            throws InterruptedException {
        OrderEvent orderEvent = updateOrderStatus(orderId, Status.DELIVERED);
        orderEventProducer.publishOrderEvent(orderEvent);
    }

    public void contactCustomer(long orderId)
            throws InterruptedException {
        OrderEvent orderEvent = updateOrderStatus(orderId, Status.ATTEMPTED_DELIVERY);
        orderEventProducer.publishOrderEvent(orderEvent);
    }

    private OrderEvent updateOrderStatus(long orderId, Status status)
            throws InterruptedException {
        OrderStatus orderStatus = orderStatusRepository.findById(orderId).orElseThrow();

        orderStatus.setStatus(status);

        orderStatusRepository.save(orderStatus);


        OrderEvent orderEvent = new OrderEvent(orderStatus
                .getOrder()
                .getItemId(), orderId, status);

        // simulate order processing time
        Thread.sleep(3000);

        return orderEvent;
    }

}
