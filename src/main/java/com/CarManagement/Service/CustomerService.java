package com.CarManagement.Service;

import com.CarManagement.Model.Customer;
import com.CarManagement.Model.Response;

import java.text.ParseException;

public interface CustomerService {

    public Response create(Customer customer) throws ParseException;
    public Response updateCustomer(Long id,Customer customer) throws ParseException;
    public Response findCustomer(Long id);
    public Response findAllCustomers();
    public Response deleteCustomer(Long id);
}
