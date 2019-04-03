package com.mes.gotogether.repositories.domain;

import com.mes.gotogether.domains.Address;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AddressRepository extends ReactiveMongoRepository<Address, ObjectId> {

    Flux<Address> findAddressByStreetNameAndHouseNumberAndCityAndCountryOrderByLastModified(String streetName,
                                                                           String houseNumber,
                                                                           String city,
                                                                           String country);

    Flux<Address> findAddressByLatitudeAndLongitudeOrderByLastModified(String latitude, String longitude);

    Mono<Void> deleteAddressByStreetNameAndHouseNumberAndCityAndCountryAnd(String streetName,
                                                                           String houseNumber,
                                                                           String city,
                                                                           String country);

    Mono<Void> deleteAddressByLatitudeAndLongitude(String latitude, String longitude);
}
