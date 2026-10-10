package dev.ankitkumar.ordereventtracker.dto;

import dev.ankitkumar.ordereventtracker.entity.Status;

public record OrderEvent(long itemId, long orderId, Status status) {

}
