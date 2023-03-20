package com.CarManagement.Service.Impl;

import com.CarManagement.Model.*;
import com.CarManagement.Repository.CarRepository;
import com.CarManagement.Repository.ContractRepository;
import com.CarManagement.Repository.CustomerRepository;
import com.CarManagement.Service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private static final Logger log = Logger.getLogger(ContractServiceImpl.class.getName());
    private final ContractRepository contractRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    String regex = "^[A-Za-z]{3}[0-9]{3}$";

    LocalDateTime date1 = LocalDateTime.now();
    @Override
    public Response createContract(ContractRequest contractRequest) throws ParseException {

        Contract contract = new Contract();

        String inputDate = contractRequest.getStrat_date(); //example input date to validate
        LocalDate currentDate = LocalDate.now(); //get current date

        //parse the input date string into LocalDate object
        LocalDate dateToValidate = LocalDate.parse(inputDate);

        Car carTest = carRepository.findByPlate(contractRequest.getPlate()).get();


        if(carTest.getContractCode() == null){

            if (contractRequest.getPlate().matches(regex)) {

                Customer customer = customerRepository.findById(contractRequest.getClientId()).get();
                Car car = carRepository.findByPlate(contractRequest.getPlate().toUpperCase()).get();

                if(dateToValidate.isAfter(currentDate)) {

                    int fullValue = contractRequest.getDuration() * car.getValue_per_day();
                    int percentage = (int) (fullValue * 0.75);

                    if(contractRequest.getCanceled_value() >= percentage ){
                        contract.setDuration(contractRequest.getDuration());
                        contract.setCode(contractRequest.getCode());
                        contract.setValue_perday_contract(car.getValue_per_day());
                        contract.setFull_value(car.getValue_per_day() * contractRequest.getDuration());
                        contract.setCanceled_value(contractRequest.getCanceled_value());
                        contract.setCustomer(customer);
                        contract.setCar(car);
                        contract.setStrat_date(contractRequest.getStrat_date());
                        contract.setRemaining_value(
                                (car.getValue_per_day() * contractRequest.getDuration()) - contractRequest.getCanceled_value()
                        );

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startdate = dateFormat.parse(contractRequest.getStrat_date());

                        LocalDateTime startdate2 = LocalDateTime.ofInstant(startdate.toInstant(), ZoneId.systemDefault());
                        Duration duration = Duration.between( date1,startdate2);

                        long days = duration.toDays();
                        contract.setDays_arrears((int) days);

                        contract.setDelinquent_balance(
                                (int) (days * (2* car.getValue_per_day()))
                        );
                        contract.setTotal_balance(
                                (int) (days * (2* car.getValue_per_day())) +
                                        ( (car.getValue_per_day() * contractRequest.getDuration()) - contractRequest.getCanceled_value())
                        );

                        Contract  contract1 = contractRepository.save(contract);

                        car.setContractCode(contract1.getCode());
                        carRepository.save(car);
                        log.info("contract created");
                        return Response.builder()
                                .body("contract created")
                                .data(Map.of("contract",contract1))
                                .HttpStatus(HttpStatus.OK.value())
                                .build();
                    }else{
                        log.info("CanceledValue most be <= 75% of fullValue");
                        return Response.builder()
                                .body("CanceledValue most be <= 75% of fullValue")
                                .HttpStatus(HttpStatus.BAD_REQUEST.value())
                                .build();
                    }


                } else {
                    log.info("the start Date is invalid ");
                    return Response.builder()
                            .body("the start Date is invalid ")
                            .HttpStatus(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            }else{
                log.info("the plate doesn't respect the format");
                return Response.builder()
                        .body("the plate doesn't respect the format")
                        .HttpStatus(HttpStatus.BAD_REQUEST.value())
                        .build();
            }
        }else{
            log.info("car already in rent");
            return Response.builder()
                    .body("car already in rent")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public Response findContract(String code) {

        if(contractRepository.existsByCode(code)){

            Contract contract = contractRepository.findByCode(code).get();

            log.info("contract with code "+code);
            return Response.builder()
                    .body("contract with code "+code)
                    .data(Map.of("contract" , contract))
                    .HttpStatus(HttpStatus.OK.value())
                    .build();
        }else{

            log.info("contract with code "+code+" not found");
            return Response.builder()
                    .body("contract with code "+code+" not found")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public Response findAllContract() {

        List<Contract> contracts = contractRepository.findAll();
        log.info("all the contract");
        return Response.builder()
                .body("all the contract")
                .data(Map.of("contract" , contracts))
                .HttpStatus(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Response closeContract(String code,Integer canceledValue) {

            if(contractRepository.existsByCode(code)){
                Contract contract = contractRepository.findByCode(code).get();

                if(Objects.equals(canceledValue, contract.getTotal_balance())){

                    contractRepository.delete(contract);

                    log.info("contract close");
                    return Response.builder()
                            .body("contract close")
                            .HttpStatus(HttpStatus.OK.value())
                            .build();

                }else{
                    log.info("the canceledValue must equal the exact total");
                    return Response.builder()
                            .body("the canceledValue must equal the exact total")
                            .HttpStatus(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            }else{
                log.info("contract with code "+code+" not found");
                return Response.builder()
                        .body("contract with code "+code+" not found")
                        .HttpStatus(HttpStatus.BAD_REQUEST.value())
                        .build();
            }

    }
}
