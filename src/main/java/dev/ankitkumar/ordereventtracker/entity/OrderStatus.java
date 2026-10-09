package dev.ankitkumar.ordereventtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    private  LocalDateTime timeStamp  = LocalDateTime.now();

}
