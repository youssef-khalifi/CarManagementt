package com.CarManagement.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "[1-9]\\d{3}")
    private String code;
    private String strat_date;
    private Integer duration;
    private Integer value_perday_contract;  //Value of the car per day at the time of contract registration
    private Integer full_value;       //Total amount to be paid by the customer
    private Integer canceled_value;   //Amount that the customer has paid so far
    private Integer remaining_value;  //Value that the customer  still has to pay at the time of delivery the car
    private Integer days_arrears;     //Number of days elapsed since day on which the client has owed  deliver the car and  today

    //Extra value that the client must pay for the days of arrears
    //It is calculated as the number of days past due multiplied for 2 times the daily rental value of the car.
    private Integer delinquent_balance;

    private Integer total_balance;
    @ManyToOne
    private Car car;

    @ManyToOne
    private Customer customer;

}
