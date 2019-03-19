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
    private String originTarget;
    private String originTargetLat;
    private String originTargetLon;
    private String originSearchRadius;
    private String destinationTarget;
    private String destinationTargetLat;
    private String destinationTargetLon;
    private String destinationSearchRadius;
    private boolean isActive;


}
