package com.mes.gotogether.domains;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

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
    private Date lastModified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetName.equals(address.streetName) &&
                houseNumber.equals(address.houseNumber) &&
                city.equals(address.city) &&
                Objects.equals(zipcode, address.zipcode) &&
                Objects.equals(state, address.state) &&
                country.equals(address.country) &&
                Objects.equals(latitude, address.latitude) &&
                Objects.equals(longitude, address.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, houseNumber, city, zipcode, state, country, latitude, longitude);
    }
}
