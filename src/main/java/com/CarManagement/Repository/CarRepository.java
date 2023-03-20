package com.CarManagement.Repository;

import com.CarManagement.Model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {


    Optional<Car> findByPlate(String plate);
    boolean existsByPlate(String plate);
    List<Car> findAllByGuy(String guy);
    List<Car> findAllByBrand(String brand);

    List<Car> findAllByContractCodeIsNull();


}
