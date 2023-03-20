package com.CarManagement.Service;

import com.CarManagement.Model.Car;
import com.CarManagement.Model.Response;

public interface CarService {

    public Response createCar(Car car);
    public Response updateCar(Car car,String plate);
    public Response findCar(String plate);
    public Response deleteCar(String plate);
    public Response findCarsByTypes(String guy);
    public Response findCarsByBrands(String brand);

    public Response carsAvailable();


}
