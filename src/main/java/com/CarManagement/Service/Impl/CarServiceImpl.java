package com.CarManagement.Service.Impl;

import com.CarManagement.Model.Car;
import com.CarManagement.Model.Response;
import com.CarManagement.Repository.CarRepository;
import com.CarManagement.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private static final Logger log = Logger.getLogger(CarServiceImpl.class.getName());
    String regex = "^[A-Za-z]{3}[0-9]{3}$";

    List<String> guys = List.of("van","sedan","hatchback","suv");
    @Override
    public Response createCar(Car car1) {

        Car car = new Car();

// plate test if respect thr format ABC123
        if (car1.getPlate().matches(regex)) {

            // test if the passengers number is greater than 1
          if(car1.getNumber_passengers() > 1){

              if(car1.getAir_conditioning().equals("yeah") || car1.getAir_conditioning().equals("no")){

                  if(guys.contains(car1.getGuy().toLowerCase())){
                      car.setPlate(car1.getPlate().toUpperCase());

                      car.setBrand(car1.getBrand().toUpperCase());
                      car.setModel(car1.getModel());
                      car.setYear(car1.getYear());
                      car.setColor(car1.getColor());
                      car.setGuy(car1.getGuy().toUpperCase());
                      car.setNumber_passengers(car1.getNumber_passengers());
                      car.setValue_per_day(car1.getValue_per_day());
                      car.setMileage(car1.getMileage());
                      car.setAir_conditioning(car1.getAir_conditioning());
                      Car saveCar = carRepository.save(car);

                      log.info("The car created "+saveCar);
                      return Response.builder()
                              .body("The car created ")
                              .HttpStatus(HttpStatus.OK.value())
                              .data(Map.of("car" , saveCar))
                              .build();

                  }else{
                      log.info(car1.getGuy() + " is not a type");
                      return Response.builder()
                              .body( car1.getGuy() + " is not a type")
                              .HttpStatus(HttpStatus.BAD_REQUEST.value())
                              .build();
                  }

              }else{
                  log.info("the air conditioning must be yeah or no");
                  return Response.builder()
                          .body("the air conditioning must be yeah or no")
                          .HttpStatus(HttpStatus.BAD_REQUEST.value())
                          .build();

              }

          }else{
              log.info("number of passengers must be greater than 1");
              return Response.builder()
                      .body("number of passengers must be greater than 1")
                      .HttpStatus(HttpStatus.BAD_REQUEST.value())
                      .build();
          }

        } else {
            log.info("plate not respect the format");
           return Response.builder()
                   .body("plate not respect the format")
                   .HttpStatus(HttpStatus.BAD_REQUEST.value())
                   .build();
        }

    }

    @Override
    public Response updateCar(Car car, String plate) {

        if(carRepository.existsByPlate(plate)){

            Car car1 = carRepository.findByPlate(plate.toUpperCase()).get();

            if(car.getGuy() != null && guys.contains(car.getGuy().toLowerCase())){
                car1.setGuy(car.getGuy().toUpperCase());

                if(car.getBrand() != null){
                    car1.setBrand(car.getBrand());
                }
                if(car.getModel() != null){
                    car1.setModel(car.getModel());
                }
                if(car.getYear() != null){
                    car1.setYear(car.getYear());
                }
                if(car.getColor() != null){
                    car1.setColor(car.getColor());
                }
                if(car.getPlate() != null && car.getPlate().matches(regex)){
                    car1.setPlate(car.getPlate().toUpperCase());
                }
                if(car.getNumber_passengers() != null && car.getNumber_passengers() <=1){
                    car1.setNumber_passengers(car.getNumber_passengers());
                }
                if(car.getMileage() != null){
                    car1.setMileage(car.getMileage());
                }
                if(car.getAir_conditioning() != null && (car.getAir_conditioning().equals("yeah")
                        || car.getAir_conditioning().equals("no") )){

                    car1.setAir_conditioning(car.getAir_conditioning());
                }
                if(car.getValue_per_day() != null){
                    car1.setValue_per_day(car.getValue_per_day());
                }
                Car carupdate = carRepository.save(car1);
                log.info("the car with the plate "+plate+" updated successfully");
                return Response.builder()
                        .body("the car with the plate "+plate+" updated successfully")
                        .data(Map.of("updated car" , carupdate))
                        .HttpStatus(HttpStatus.OK.value())
                        .build();
            }else{
                log.info(car.getGuy() + " is not a type");
                return  Response.builder()
                        .body(car.getGuy() + " is not a type")
                        .HttpStatus(HttpStatus.BAD_REQUEST.value())
                        .build();
            }

        }else{
            log.info("car with the plate "+plate+" not found");
            return Response.builder()
                    .body("car with the plate "+plate+" not found")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

    }

    @Override
    public Response findCar(String plate) {
        if(carRepository.existsByPlate(plate)){

            Car car = carRepository.findByPlate(plate.toUpperCase()).get();
            log.info("Car with the plate "+plate);
            return Response.builder()
                    .body("Car with the plate "+plate)
                    .HttpStatus(HttpStatus.OK.value())
                    .data(Map.of("car",car))
                    .build();
        }else{
            log.info("car with plate "+plate+" not found");
            return Response.builder()
                    .body("car with plate "+plate+" not found")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

    }

    @Override
    public Response deleteCar(String plate) {

        if(carRepository.existsByPlate(plate.toUpperCase())){

            Car car = carRepository.findByPlate(plate.toUpperCase()).get();

            carRepository.delete(car);
            log.info("car with plate "+plate.toUpperCase()+" deleted successfully");
            return Response.builder()
                    .body("car with plate "+plate.toUpperCase()+" deleted successfully")
                    .HttpStatus(HttpStatus.OK.value())
                    .build();
        }else{
            log.info("car with plate "+plate.toUpperCase()+" not found");
            return Response.builder()
                    .body("car with plate "+plate.toUpperCase()+" not found")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public Response findCarsByTypes(String guy) {

        if(guys.contains(guy.toLowerCase())){
            List<Car>  cars = carRepository.findAllByGuy(guy.toUpperCase());

            log.info("find all cars by type "+guy.toUpperCase());
            return Response.builder()
                    .body("find all cars by type "+guy.toUpperCase())
                    .data(Map.of("cars" ,cars ))
                    .HttpStatus(HttpStatus.OK.value())
                    .build();

        }else{
            log.info("type not valid");
            return Response.builder()
                    .body("type not valid")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public Response findCarsByBrands(String brand) {

        List<Car> cars = carRepository.findAllByBrand(brand);
        log.info("cars with the brand "+brand.toUpperCase());
        return Response.builder()
                .data(Map.of("cars" , cars))
                .body("cars with the brand "+brand.toUpperCase())
                .HttpStatus(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Response carsAvailable() {

        List<Car> cars = carRepository.findAllByContractCodeIsNull();
        return Response.builder()
                .body("cars available")
                .HttpStatus(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
