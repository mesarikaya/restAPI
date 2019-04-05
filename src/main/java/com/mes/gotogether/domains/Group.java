package com.mes.gotogether.domains;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;


@Data
@Document
public class Group {

    @Id
    private ObjectId id;
    private String name;
    private HashSet<User> members;
    private HashSet<User> owners;
    private Address originAddress;
    private String originSearchRadius;
    private Address destinationAddress;
    private String destinationSearchRadius;
    private boolean isActive;
}
