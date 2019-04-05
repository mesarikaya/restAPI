package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Group;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GroupService {

    Mono<Group> findById(ObjectId id);
    Flux<Group> findGroupsByOriginAddress(Address originAddress);
    Flux<Group> findGroupsByDestinationAddress(Address destinationAddress);
    Flux<Group> findGroupsByOriginAndDestinationAddress(Address originAddress, Address destinationAddress);
    Flux<Group> findGroupsByOriginAndDestinationGeoLocationDetails(
            double originLatitude,
            double originLongitude,
            double destinationLatitude,
            double destinationLongitude
    );

    Flux<Group> findGroupsByOriginWithinSearchRadius(
            double originLatMin, double originLatMax,
            double originLongMin, double originLongMax);

    Flux<Group> findGroupsByDestinationWithinSearchRadius(
            double destLatMin, double destLatMax,
            double destLongMin, double destLongMax);

    Flux<Group> findGroupsByOriginAndDestinationWithinSearchRadius(
            double originLatMin, double originLatMax,
            double originLongMin, double originLongMax,
            double destLatMin, double destLatMax,
            double destLongMin, double destLongMax);

    Flux<Group> findAll();

    Mono<Group> saveOrUpdate(Group group);

    Mono<Void> deleteById(ObjectId id);
    Mono<Void> deleteAll();
}
