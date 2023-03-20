package com.CarManagement.Controller;

import com.CarManagement.Model.ContractRequest;
import com.CarManagement.Model.Response;
import com.CarManagement.Service.Impl.ContractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ContractController {

    private final ContractServiceImpl contractService;

    @PostMapping("contract")
    public Response createContract(@RequestBody ContractRequest contractRequest) throws ParseException {

        return contractService.createContract(contractRequest);
    }

    @GetMapping("contract/{code}")
    public Response findContract(@PathVariable String code){
        return contractService.findContract(code);
    }

    @GetMapping("contract")
    public Response findAllContract(){

        return contractService.findAllContract();
    }

    @DeleteMapping("contract/{code}/{canceledValue}")
    public Response cancelContract(@PathVariable String code, @PathVariable Integer canceledValue){

        return contractService.closeContract(code,canceledValue);
    }
}
