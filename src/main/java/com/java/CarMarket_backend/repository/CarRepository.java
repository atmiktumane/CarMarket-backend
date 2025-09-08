package com.java.CarMarket_backend.repository;

import com.java.CarMarket_backend.model.CarModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<CarModel, String> {
}
