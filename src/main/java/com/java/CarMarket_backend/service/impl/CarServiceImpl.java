package com.java.CarMarket_backend.service.impl;

import com.java.CarMarket_backend.dto.CarDTO;
import com.java.CarMarket_backend.model.CarModel;
import com.java.CarMarket_backend.repository.CarRepository;
import com.java.CarMarket_backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public CarDTO addCar(CarDTO carDTO) {
        // Current Time
        carDTO.setCreatedAt(LocalDateTime.now());

        // Save in DB
        CarModel car = carRepository.save(carDTO.convertToCarEntity());

        // Response body
        return car.convertToCarDTO();
    }

    @Override
    public List<CarDTO> getAllCars() {
        // Return All Car List from DB
        return carRepository.findAll().stream().map((x)-> x.convertToCarDTO()).toList();
    }
}
