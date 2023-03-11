package com.ourkitchen.yourhealth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderOneDay {
    @Id
    private String id;
    private LocalDate realizationDate;
    private List<String> mealsIds;
    private List<String> additionalProductsIds;
}
