package com.mes.gotogether.domains;

import java.util.HashSet;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Document
public class Group {

    @Id
    private ObjectId id;
    @NotNull
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

    public HashSet<User> getMembers() {
        return members;
    }

    public void setMembers(HashSet<User> members) {
        this.members = members;
    }

    public HashSet<User> getOwners() {
        return owners;
    }

    public void setOwners(HashSet<User> owners) {
        this.owners = owners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id.equals(group.id) &&
                name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
