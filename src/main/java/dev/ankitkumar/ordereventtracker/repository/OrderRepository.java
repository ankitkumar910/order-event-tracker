package dev.ankitkumar.ordereventtracker.repository;

import dev.ankitkumar.ordereventtracker.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
