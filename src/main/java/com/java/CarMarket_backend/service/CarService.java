package com.java.CarMarket_backend.service;

import com.java.CarMarket_backend.dto.CarAnalyticsDTO;
import com.java.CarMarket_backend.dto.CarDTO;
import com.java.CarMarket_backend.dto.ResponseDTO;

import java.util.List;

public interface CarService {
    CarDTO addCar(String user_id, CarDTO carDTO);

    List<CarDTO> getAllCars();

    List<CarDTO> getAllActiveCars(String search);

    List<CarDTO> getAllSellerCars(String user_id);

    CarDTO updateCar(CarDTO carDTO) throws Exception;

    ResponseDTO deleteCar(String id) throws Exception;

    CarAnalyticsDTO getCarAnalytics();
}
