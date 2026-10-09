package dev.ankitkumar.ordereventtracker.service;


import dev.ankitkumar.ordereventtracker.dto.OrderStatusResponseDto;
import dev.ankitkumar.ordereventtracker.entity.Order;
import dev.ankitkumar.ordereventtracker.entity.OrderStatus;
import dev.ankitkumar.ordereventtracker.entity.Status;
import dev.ankitkumar.ordereventtracker.repository.OrderRepository;
import dev.ankitkumar.ordereventtracker.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderStatusRepository orderStatusRepository;
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

        return OrderStatusResponseDto
                .builder()
                .message("Order received.")
                .order_id(savedOrder.getId())
                .status(Status.PLACED).build();

    }
}
