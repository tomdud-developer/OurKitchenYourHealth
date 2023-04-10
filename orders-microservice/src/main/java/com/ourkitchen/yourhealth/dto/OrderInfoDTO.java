package com.ourkitchen.yourhealth.dto;

import com.ourkitchen.yourhealth.model.OrderOneDay;
import com.ourkitchen.yourhealth.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderInfoDTO {

    private String id;
    private String userId;
    private LocalDateTime startDate;
    private Long daysNumber;

    private List<OrderOneDay> orderOneDays;
    private BigDecimal totalPrice;

    private Boolean isConfirmed;

    private OrderStatus orderStatus;
}
