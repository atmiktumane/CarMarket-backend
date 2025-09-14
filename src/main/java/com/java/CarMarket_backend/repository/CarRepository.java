package com.java.CarMarket_backend.repository;

import com.java.CarMarket_backend.model.CarModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<CarModel, String> {
    // below list will store list of cars in desc sorted order w.r.t createdAt (recently added on top)
    List<CarModel> findByUserIdOrderByCreatedAtDesc(String userId);
}
