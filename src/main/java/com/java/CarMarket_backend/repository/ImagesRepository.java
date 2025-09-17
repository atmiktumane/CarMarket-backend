package com.java.CarMarket_backend.repository;

import com.java.CarMarket_backend.model.ImagesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImagesRepository extends MongoRepository<ImagesModel, String> {
    List<ImagesModel> findByCarId(String carId);
}
