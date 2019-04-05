package com.mes.gotogether.domains;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;


@Data
@NoArgsConstructor
@Document
public class Group {

    @Id
    private ObjectId id;
    private String name;
    private HashSet<User> members;
    private HashSet<User> owners;
    private Address originAddress;
    private double originSearchRadius;
    private Address destinationAddress;
    private double destinationSearchRadius;
    private boolean isActive;

    public Group(Group group){
        this(
                group.getId(),
                group.getName(),
                group.getMembers(),
                group.getOwners(),
                group.getOriginAddress(),
                group.getOriginSearchRadius(),
                group.getDestinationAddress(),
                group.getDestinationSearchRadius(),
                group.isActive()
        );
    }

    @PersistenceConstructor
    public Group(ObjectId id, String name, HashSet<User> members,
                 HashSet<User> owners, Address originAddress,
                 double originSearchRadius, Address destinationAddress,
                 double destinationSearchRadius, boolean isActive) {

        this.id = id;
        this.name = name;
        this.members = members;
        this.owners = owners;
        this.originAddress = originAddress;
        this.originSearchRadius = originSearchRadius;
        this.destinationAddress = destinationAddress;
        this.destinationSearchRadius = destinationSearchRadius;
        this.isActive = isActive;
    }
}
