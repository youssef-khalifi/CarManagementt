package com.CarManagement.Model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    private String body;
    private Map<?,?> data;
    private Integer HttpStatus;
}
