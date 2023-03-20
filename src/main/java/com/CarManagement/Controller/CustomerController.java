package com.CarManagement.Controller;

import com.CarManagement.Model.Customer;
import com.CarManagement.Model.Response;
import com.CarManagement.Service.Impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping("customer")
    public Response createCustomer(@RequestBody Customer customer) throws ParseException {

        return customerService.create(customer);
    }

    @PutMapping("customer/{id}")
    public Response updateCustomer(@PathVariable Long id , @RequestBody Customer customer) throws ParseException {

        return customerService.updateCustomer(id,customer);
    }

    @GetMapping("customer/{id}")
    public Response findCustomer(@PathVariable Long id){

        return customerService.findCustomer(id);
    }
    @GetMapping("customers")
    public Response findAllCustomers(){

        return customerService.findAllCustomers();
    }

    @DeleteMapping("customer/{id}")
    public Response deleteCustomer(@PathVariable Long id){

        return customerService.deleteCustomer(id);
    }
}
