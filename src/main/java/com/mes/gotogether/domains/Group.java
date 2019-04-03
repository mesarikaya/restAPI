package com.mes.gotogether.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;


@Data
@Document
public class Group {

    @Id
    private String id;
    private HashSet<User> members;
    private String originAddress;
    private String originAddressLatitude;
    private String originAddressLongitude;
    private String originSearchRadius;
    private String destinationAddress;
    private String destinationAddressLatitude;
    private String destinationAddressLongitude;
    private String destinationSearchRadius;
    private boolean isActive;
}
