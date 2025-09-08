package com.java.CarMarket_backend.controller;

import com.java.CarMarket_backend.dto.CarDTO;
import com.java.CarMarket_backend.dto.ResponseDTO;
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
    @PostMapping("/add")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO){
        return new ResponseEntity<>(carService.addCar(carDTO), HttpStatus.CREATED);
    }

    // GET ALL - Cars
    @GetMapping("/get-all")
    public ResponseEntity<List<CarDTO>> getAllCars(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
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

}
