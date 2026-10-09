package dev.ankitkumar.ordereventtracker.controller;

import dev.ankitkumar.ordereventtracker.dto.OrderStatusResponseDto;
import dev.ankitkumar.ordereventtracker.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderStatusResponseDto> placeOrder(@RequestParam(name = "itemId") long itemId) {


        if (itemId < 1 || itemId > 12) throw
                new IllegalArgumentException("Invalid order id passed.");


        return ResponseEntity.ok(orderService.createOrder(itemId));
    }

    @GetMapping("")
    public String home() {
        return """
                Available products:
                ID   Product
                1    Mango
                2    Apple
                3    Bat
                4    Toy Car
                5    Headphone
                6    Smart phone
                7    Bottle
                8    Laptop
                9    Chair
                10   Pen
                11   Bag
                12   Books
                """;
    }
}

