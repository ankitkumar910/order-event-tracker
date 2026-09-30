package dev.ankitkumar.ordereventtracker.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @PostMapping("")
    public String placeOrder(@RequestParam(name = "itemId") long itemid){


        if(itemid < 1 || itemid > 12) throw
                new IllegalArgumentException("Invalid order id passed.");

        System.out.println("item : " + itemid);



        return "Order Placed.";
    }

    @GetMapping("")
    public String home(){
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

