package com.java.CarMarket_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarAnalyticsDTO {
    private Map<String, Long> statusCounts;
    private Map<String, Long> conditionCounts;
    private List<Map.Entry<String, Long>> topModels;
}
