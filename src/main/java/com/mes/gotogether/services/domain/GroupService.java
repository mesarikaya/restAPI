package com.mes.gotogether.services.domain;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Group;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GroupService {

    Mono<Group> findById(ObjectId id);
    Flux<Group> findGroupsByOriginAddress(Address originAddress);
    Flux<Group> findGroupsByDestinationAddress(Address destinationAddress);
    Flux<Group> findGroupsByName(String groupName);

    Flux<Group> findGroupsByOriginAndDestinationAddress(Address originAddress, Address destinationAddress);
    
    Flux<Group> findGroupsByOriginAndDestinationAddress(String originAddress, String destinationAddress, 
    													double originRadius, double destinationRadius,
    													Pageable page);
    
    Flux<Group> findGroupsByOriginAndDestinationGeoLocationDetails(
            Double originLatitude,
            Double originLongitude,
            Double destinationLatitude,
            Double destinationLongitude
    );

    Flux<Group> findGroupsByOriginWithinThresholds(
            Double originLatMin, Double originLatMax,
            Double originLongMin, Double originLongMax);

    Flux<Group> findGroupsByDestinationWithinThresholds(
            Double destLatMin, Double destLatMax,
            Double destLongMin, Double destLongMax);

    Flux<Group> findGroupsByOriginAndDestinationWithinThresholds(
            Double originLatMin, Double originLatMax,
            Double originLongMin, Double originLongMax,
            Double destLatMin, Double destLatMax,
            Double destLongMin, Double destLongMax,
            Pageable page);

    Flux<Group> findGroupsByOriginWithinRadius(
            Double originLat, Double originLong, Double originRadius);

    Flux<Group> findGroupsByDestinationWithinRadius(
            Double destLat, Double destLong, Double destRadius);

    Flux<Group> findGroupsByOriginAndDestinationWithinRadius(
            Double originLat, Double originLong, Double originRadius,
            Double destLat, Double destLong, Double destRadius,
            Pageable page);

    Flux<Group> findAll();

    Mono<Group> saveOrUpdate(Group group);
    Mono<Group> saveFakeGroup(Group group);

    Mono<Void> deleteById(ObjectId id);
    Mono<Void> deleteAll();
}
