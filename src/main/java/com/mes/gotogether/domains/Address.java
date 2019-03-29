package com.mes.gotogether.domains;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Address {

    @Id
    private ObjectId id;
    private String streetName;
    private String houseNumber;
    private String city;
    private String zipcode;
    private String state;
    private String country;
    private String latitude;
    private String longitude;

}
