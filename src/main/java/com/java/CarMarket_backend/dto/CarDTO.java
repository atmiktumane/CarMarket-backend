package com.java.CarMarket_backend.dto;

import com.java.CarMarket_backend.model.CarCondition;
import com.java.CarMarket_backend.model.CarModel;
import com.java.CarMarket_backend.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private String id;
    private String name;
    private String model;
    private String description;
    private Integer price;
    private Integer first_purchase_year;
    private LocalDateTime createdAt;
    private Integer mileage;
    private String location;
    private CarCondition condition;
    private CarStatus status;

    public CarModel convertToCarEntity(){
        return new CarModel(this.id, this.name, this.model, this.description, this.price, this.first_purchase_year, this.createdAt, this.mileage, this.location, this.condition, this.status);
    }
}
