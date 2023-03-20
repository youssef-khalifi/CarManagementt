package com.CarManagement.Repository;

import com.CarManagement.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract,Long> {


    Optional<Contract> findByCode(String code);

    boolean existsByCode(String code);
}
