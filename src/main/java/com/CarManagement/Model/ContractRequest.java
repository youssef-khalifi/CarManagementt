package com.CarManagement.Model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractRequest {

    private String plate;
    private Long clientId;
    @Pattern(regexp = "[1-9]\\d{3}")
    private String code;
    private String strat_date;
    private Integer duration;

    private Integer canceled_value;   //Amount that the customer has paid so far



}
