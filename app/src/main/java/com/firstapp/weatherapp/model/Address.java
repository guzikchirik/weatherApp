package com.firstapp.weatherapp.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {
    private Integer id;
    private String street;
    private String city;
    private String building;
}
