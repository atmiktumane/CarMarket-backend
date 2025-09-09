package com.java.CarMarket_backend.service.impl;

import com.java.CarMarket_backend.dto.CarDTO;
import com.java.CarMarket_backend.dto.ResponseDTO;
import com.java.CarMarket_backend.exception.ResourceNotFoundException;
import com.java.CarMarket_backend.model.CarModel;
import com.java.CarMarket_backend.model.CarStatus;
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
    public CarDTO addCar(String user_id, CarDTO carDTO) {
        // Current Time
        carDTO.setCreatedAt(LocalDateTime.now());

        carDTO.setStatus(CarStatus.ACTIVE);

        carDTO.setUserId(user_id);

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

    @Override
    public List<CarDTO> getAllActiveCars() {

        // Filter and return only Active Car List
        List<CarDTO> carDTOList = carRepository.findAll()
                .stream()
                .filter((x)-> x.getStatus() == CarStatus.ACTIVE)
                .map((x)-> x.convertToCarDTO())
                .toList();

        // response
        return carDTOList;
    }

    @Override
    public List<CarDTO> getAllSellerCars(String user_id) {
        List<CarDTO> carDTOList = carRepository.findAll()
                .stream()
                .filter((x)-> x.getUserId().equals(user_id))
                .map((x)-> x.convertToCarDTO())
                .toList();

        return carDTOList;
    }

    @Override
    public CarDTO updateCar(CarDTO carDTO) throws Exception {
        // Find Car By ID (present in carDTO)
        carRepository.findById(carDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("Car not found"));

        // Save Car data in DB
        carRepository.save(carDTO.convertToCarEntity());

        return carDTO;
    }

    @Override
    public ResponseDTO deleteCar(String id) throws Exception {
        CarModel existingCar = carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Car not found"));

        carRepository.delete(existingCar);
        return new ResponseDTO("Car deleted successfully");
    }
}
