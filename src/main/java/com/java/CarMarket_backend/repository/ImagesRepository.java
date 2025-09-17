package com.java.CarMarket_backend.repository;

import com.java.CarMarket_backend.model.ImagesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends MongoRepository<ImagesModel, String> {
    List<ImagesModel> findByCarId(String carId);

    // faster retrivel of data (fetch only single image from Database)
    Optional<ImagesModel> findFirstByCarId(String carId);
}
