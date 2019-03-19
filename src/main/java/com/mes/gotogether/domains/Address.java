package com.mes.gotogether.domains;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Address {

    @Id
    private String id;
    private String streetName;
    private String houseNumber;
    private String city;
    private String zipcode;
    private String state;
    private String country;
    private String lat;
    private String lon;

}
