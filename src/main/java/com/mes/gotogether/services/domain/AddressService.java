package com.mes.gotogether.services.domain;

import org.bson.types.ObjectId;

import com.mes.gotogether.domains.Address;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AddressService {

    Mono<Address> findAddressById(ObjectId id);
    Mono<Address> findAddressByStreetNameAndHouseNumberAndCityAndCountry(String streetName,
                                                                          String houseNumber,
                                                                          String city,
                                                                          String country);

    Flux<Address> findAddressByLatitudeAndLongitudeAnd(Double latitude, Double longitude);
    Flux<Address> findAll();
    Mono<Address> saveOrUpdateAddress(Address address);
    Mono<Address> saveFakeAddress(Address address);
    Mono<Void> deleteAddressById(ObjectId id);
    Mono<Void> deleteAddressByStreetNameAndHouseNumberAndCityAndCountryAnd(String streetName,
                                                                           String houseNumber,
                                                                           String city,
                                                                           String country);

    Mono<Void> deleteAddressByLatitudeAndLongitude(Double latitude, Double longitude);
    Mono<Void> deleteAll();
    Mono<Address> setAddressLatitudeAndLongitude(Address address);
}
