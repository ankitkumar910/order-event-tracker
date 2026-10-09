package dev.ankitkumar.ordereventtracker.repository;

import dev.ankitkumar.ordereventtracker.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {

}
