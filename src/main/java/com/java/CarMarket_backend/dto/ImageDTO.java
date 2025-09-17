package com.java.CarMarket_backend.dto;

import com.java.CarMarket_backend.model.ImagesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO { // DTO for returning image info
    private String id;
    private String image; // base64 string
    private String carId;

    public ImagesModel convertToImageEntity(){
        return new ImagesModel(
                this.id,
                this.image!=null ? Base64.getDecoder().decode(this.image) : null,
                this.carId
        );
    }

}



