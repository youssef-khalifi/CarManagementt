package com.CarManagement.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String birthday;
    @Digits(integer = 9, fraction = 0, message = "Number must have 9 digits")
    private Long number_Id;
    private String due_date;
    @Pattern(regexp = "\\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}")
    private String phoneNumber;

}
