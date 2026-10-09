package dev.ankitkumar.ordereventtracker.dto;

import dev.ankitkumar.ordereventtracker.entity.Status;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderStatusResponseDto {
    private String message;
    private Status status;
    private long order_id;
}
