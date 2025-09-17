package com.java.CarMarket_backend.controller;

import com.java.CarMarket_backend.dto.*;
import com.java.CarMarket_backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/car")
public class CarController {
    @Autowired
    private CarService carService;

    // POST - Add Car
    @PostMapping("/add/{user_id}")
    public ResponseEntity<CarDTO> addCar(@PathVariable String user_id, @RequestBody CarDTO carDTO){
        return new ResponseEntity<>(carService.addCar(user_id, carDTO), HttpStatus.CREATED);
    }

    // GET ALL - Cars
    @GetMapping("/get-all")
    public ResponseEntity<List<CarDTO>> getAllCars(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    // GET - All Active Cars
    @GetMapping("/get-all-active")
    public ResponseEntity<List<CarDTO>> getAllActiveCars(@RequestParam(required = false) String search){
        return new ResponseEntity<>(carService.getAllActiveCars(search), HttpStatus.OK);
    }

    // GET - All Cars associated with Particular User (Seller)
    @GetMapping("/get-all/{user_id}")
    public ResponseEntity<List<CarDTO>> getAllSellerCars(@PathVariable String user_id){
        return new ResponseEntity<>(carService.getAllSellerCars(user_id), HttpStatus.OK);
    }

    // PUT - Update Car
    @PutMapping("/update")
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarDTO carDTO) throws Exception{
        return new ResponseEntity<>(carService.updateCar(carDTO), HttpStatus.OK);
    }

    // DELETE - Delete Car
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteCar(@PathVariable String id) throws Exception{
        return new ResponseEntity<>(carService.deleteCar(id), HttpStatus.OK);
    }

    // GET - Car Analytics
    @GetMapping("/analytics")
    public ResponseEntity<CarAnalyticsDTO> getCarAnalytics(){
        return new ResponseEntity<>(carService.getCarAnalytics(), HttpStatus.OK);
    }

    // POST - Upload Car Images (multiple images)
    @PostMapping("/upload-images")
    public ResponseEntity<List<ImageDTO>> uploadCarImages(@RequestBody CarImagesRequestDTO imagesRequest){
        return new ResponseEntity<>(carService.uploadCarImages(imagesRequest), HttpStatus.OK);
    }

    // GET - Get all images of a car
    @GetMapping("/get-images/{carId}")
    public ResponseEntity<List<ImageDTO>> getCarImages(@PathVariable String carId){
        return new ResponseEntity<>(carService.getCarImages(carId), HttpStatus.OK);
    }

}
