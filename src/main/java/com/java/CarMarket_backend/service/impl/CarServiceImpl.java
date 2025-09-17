package com.java.CarMarket_backend.service.impl;

import com.java.CarMarket_backend.dto.*;
import com.java.CarMarket_backend.exception.ResourceNotFoundException;
import com.java.CarMarket_backend.model.CarModel;
import com.java.CarMarket_backend.model.CarStatus;
import com.java.CarMarket_backend.model.ImagesModel;
import com.java.CarMarket_backend.repository.CarRepository;
import com.java.CarMarket_backend.repository.ImagesRepository;
import com.java.CarMarket_backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public CarDTO addCar(String user_id, CarDTO carDTO) {

        // Set id as null
        carDTO.setId(null);

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
    public List<CarDTO> getAllActiveCars(String search) {

        // Filter and return only Active Car List
        List<CarDTO> carDTOList = carRepository.findAll()
                .stream()
                .filter((car)-> car.getStatus() == CarStatus.ACTIVE)
                .filter((car)-> search == null || search.isEmpty()
                        || car.getName().toLowerCase().contains(search.toLowerCase())  // search Name or Car Model
                        || car.getModel().toLowerCase().contains(search.toLowerCase()))
                .map((car)-> car.convertToCarDTO())
                .toList();

        // response
        return carDTOList;
    }

    @Override
    public List<CarDTO> getAllSellerCars(String user_id) {
        List<CarModel> carModels = carRepository.findByUserIdOrderByCreatedAtDesc(user_id);

        List<CarDTO> carDTOList = carModels
                .stream()
                .map(car -> {
                    CarDTO carDTO = car.convertToCarDTO();

                    // Fetch images for this car
                    List<String> images = imagesRepository.findByCarId(carDTO.getId())
                        .stream()
                        .map(img -> Base64.getEncoder().encodeToString(img.getImage())) // convert byte[] → base64
                        .toList();

                    carDTO.setImages(images);

                    return carDTO;

                })
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

    @Override
    public CarAnalyticsDTO getCarAnalytics() {
        List<CarModel> cars = carRepository.findAll();

        // 1. Status counts
        Map<String, Long> statusCounts = cars.stream()
                .collect(Collectors.groupingBy(car -> car.getStatus().name(), Collectors.counting()));

        // 2. Condition counts
        Map<String, Long> conditionCounts = cars.stream()
                .collect(Collectors.groupingBy(car -> car.getCondition().name(), Collectors.counting()));

        // 3. Top 5 models with counts
        Map<String, Long> modelCounts = cars.stream()
                .collect(Collectors.groupingBy(CarModel::getModel, Collectors.counting()));

        List<Map.Entry<String, Long>> topModels = modelCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .toList();

        return new CarAnalyticsDTO(statusCounts, conditionCounts, topModels);
    }

    @Override
    public List<ImageDTO> uploadCarImages(CarImagesRequestDTO requestData) {
        List<ImagesModel> imageEntities = requestData.getImages().stream()
                .map(base64 -> new ImagesModel(
                        null,
                        Base64.getDecoder().decode(base64),
                        requestData.getCarId()
                ))
                .collect(Collectors.toList());

        return imagesRepository.saveAll(imageEntities)
                .stream()
                .map(ImagesModel::convertToImageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> getCarImages(String carId) {
        return imagesRepository.findByCarId(carId)
                .stream()
                .map(ImagesModel::convertToImageDTO)
                .collect(Collectors.toList());
    }
}
