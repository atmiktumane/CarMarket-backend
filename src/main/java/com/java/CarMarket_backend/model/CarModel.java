package com.java.CarMarket_backend.model;

import com.java.CarMarket_backend.dto.CarDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cars")
public class CarModel {
    @Id
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

    public CarDTO convertToCarDTO(){
        return new CarDTO(this.id, this.name, this.model, this.description, this.price, this.first_purchase_year, this.createdAt, this.mileage, this.location, this.condition, this.status);
    }

}
