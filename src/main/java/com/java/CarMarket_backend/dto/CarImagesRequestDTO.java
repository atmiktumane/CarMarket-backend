package com.java.CarMarket_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarImagesRequestDTO { // DTO for multiple images upload request
    private String carId;
    private List<String> images; // base64 strings
}
