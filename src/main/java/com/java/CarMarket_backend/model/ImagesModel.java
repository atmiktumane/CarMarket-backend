package com.java.CarMarket_backend.model;

import com.java.CarMarket_backend.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "car_images")
public class ImagesModel {
    @Id
    private String id;
    private byte[] image; // store binary
    private String carId;

    public ImageDTO convertToImageDTO(){
        return new ImageDTO(
                this.id,
                this.image!=null ? Base64.getEncoder().encodeToString(this.image) : null,
                this.carId
        );
    }
}
