package com.CarManagement.Service.Impl;

import com.CarManagement.Model.Customer;
import com.CarManagement.Model.Response;
import com.CarManagement.Repository.CustomerRepository;
import com.CarManagement.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = Logger.getLogger(CustomerServiceImpl.class.getName());
    private final CustomerRepository customerRepository;

    @Override
    public Response create(Customer customer) throws ParseException {

        Customer customer1 = new Customer();
        String birthdayDate = customer.getBirthday();
        String dueDate = customer.getDue_date();
        LocalDate currentDate = LocalDate.now();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);


        //parse the input date string into LocalDate object
        LocalDate dateToValidate = LocalDate.parse(dueDate);


        try {
            Date date = format.parse(birthdayDate);
            String formattedDateString = format.format(date);

            Date date2 = format.parse(dueDate);
            String formattedDateString2 = format.format(date2);

            // CHECK THE FORMAT FOR DUE_DATE
            if (formattedDateString2.equals(dueDate)) {

                //CHECK IF AFTER THE CURRENT DATE
                if (dateToValidate.isAfter(currentDate)) {
                    //CHECK THE FORMAT OF BIRTHDAY DATE
                    if (formattedDateString.equals(birthdayDate)) {
                        // format valid
                        Date now = new Date();
                        long ageInMillis = now.getTime() - date.getTime();
                        int ageInYears = (int) (ageInMillis / (1000 * 60 * 60 * 24 * 365L));

                        if (ageInYears >= 18) {

                            customer1.setFirstname(customer.getFirstname());
                            customer1.setLastname(customer.getLastname());
                            customer1.setNumber_Id(customer.getNumber_Id());
                            customer1.setDue_date(customer.getDue_date());
                            customer1.setPhoneNumber(customer.getPhoneNumber());
                            customer1.setBirthday(customer.getBirthday());

                            Customer savedCustomer = customerRepository.save(customer1);

                            log.info("customer created successfully"+ savedCustomer);
                            return Response.builder()
                                    .data(Map.of("customer", savedCustomer))
                                    .body("customer created successfully")
                                    .HttpStatus(HttpStatus.OK.value())
                                    .build();

                        } else {
                            log.info("The customer is under 18 years old.");
                            return Response.builder()
                                    .body("The customer is under 18 years old.")
                                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                                    .build();
                        }

                    } else {
                        log.info("the birthday date format is not valid");
                        return Response.builder()
                                .body("the birthday date format is not valid")
                                .HttpStatus(HttpStatus.BAD_REQUEST.value())
                                .build();
                    }
                } else {
                    log.info("the due_Date is not valid !! can't be before the currentDate");
                    return Response.builder()
                            .body("the due_Date is not valid !! can't be before the currentDate")
                            .HttpStatus(HttpStatus.BAD_REQUEST.value())
                            .build();
                }


            } else {
                log.info("Due_Date not respect the format yyyy-mm-dd");
                return Response.builder()
                        .body("Due_Date not respect the format yyyy-mm-dd")
                        .HttpStatus(HttpStatus.BAD_REQUEST.value())
                        .build();
            }


        } catch (ParseException e) {
            log.info("the birthday date format is not valid");
            return Response.builder()
                    .body("the birthday date format is not valid")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public Response updateCustomer(Long id, Customer customer) throws ParseException {

        Customer client = customerRepository.findById(id).get();


        if(customer.getFirstname() != null){
            client.setFirstname(customer.getFirstname());
        }
        if(customer.getLastname() != null){
            client.setLastname(customer.getLastname());
        }
        if(customer.getNumber_Id() != null){
            client.setNumber_Id(customer.getNumber_Id());
        }
        if(customer.getPhoneNumber() != null){
            client.setPhoneNumber(customer.getPhoneNumber());
        }
        if (customer.getDue_date() != null ){
                client.setDue_date(customer.getDue_date());

        }
        if(customer.getBirthday() != null ){
                client.setBirthday(customer.getBirthday());
        }

        Customer updatedcustomer = customerRepository.save(client);

        log.info("customer updated successfully"+updatedcustomer);
        return Response.builder()
                .data(Map.of("updated customer" , updatedcustomer))
                .body("customer updated successfully")
                .HttpStatus(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Response findCustomer(Long id) {

        if (customerRepository.existsById(id)){
            Customer customer = customerRepository.findById(id).get();

            log.info("customer with the id "+id);
            return Response.builder()
                    .body("customer with the id "+id)
                    .data(Map.of("customer",customer))
                    .HttpStatus(HttpStatus.OK.value())
                    .build();
        }else {
            log.info("customer with the id "+id+" not found");
            return Response.builder()
                    .body("customer with the id "+id+" not found")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public Response findAllCustomers() {

        List<Customer> customers = customerRepository.findAll();
        log.info("all customers");
        return Response.builder()
                .body("all customers")
                .data(Map.of("customers" , customers))
                .HttpStatus(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Response deleteCustomer(Long id) {

        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            log.info("customer with the id "+id+" deleted");
            return Response.builder()
                    .body("customer with the id "+id+" deleted")
                    .HttpStatus(HttpStatus.OK.value())
                    .build();
        }else {
            log.info("customer with id "+id+" not found");
            return Response.builder()
                    .body("customer with id "+id+" not found")
                    .HttpStatus(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

}

