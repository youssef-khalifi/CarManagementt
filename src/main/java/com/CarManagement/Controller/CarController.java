package com.CarManagement.Controller;

import com.CarManagement.Model.Car;
import com.CarManagement.Model.Response;
import com.CarManagement.Service.Impl.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CarController {

    private final CarServiceImpl carService;

    @PostMapping("car")
    public Response createCar(@RequestBody Car car){
        
       return carService.createCar(car);
    }

    @PutMapping("car/{plate}")
    public Response updateCar(@PathVariable String plate , @RequestBody Car car){

      return  carService.updateCar(car,plate);
    }

    @GetMapping("car/{plate}")
    public Response getCar(@PathVariable String plate){

        return carService.findCar(plate);
    }

    @DeleteMapping("car/{plate}")
    public Response deleteCar(@PathVariable String plate){

        return carService.deleteCar(plate);
    }

    @GetMapping("car/type/{type}")
    public Response findCarsByTypes(@PathVariable String type){

        return carService.findCarsByTypes(type);
    }

    @GetMapping("car/brand/{brand}")
    public Response findCarsByBrands(@PathVariable String brand){

        return carService.findCarsByBrands(brand);
    }

    @GetMapping("car/available")
    public Response findCarsAvailable(){

        return carService.carsAvailable();
    }
}
