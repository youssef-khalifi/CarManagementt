package com.CarManagement.Service;

import com.CarManagement.Model.Contract;
import com.CarManagement.Model.ContractRequest;
import com.CarManagement.Model.Response;

import java.text.ParseException;

public interface ContractService {

    public Response createContract(ContractRequest contract) throws ParseException;
    public Response findContract(String code);
    public Response findAllContract();
    public Response closeContract(String code,Integer canceledValue);
}
