package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AddressService {

    Mono<Address> findAddressById(ObjectId id);
    Mono<Address> findAddressByStreetNameAndHouseNumberAndCityAndCountry(String streetName,
                                                                          String houseNumber,
                                                                          String city,
                                                                          String country);


    Flux<Address> findAddressByLatitudeAndLongitudeAnd(String latitude, String longitude);
    Flux<Address> findAll();
    Mono<Address> saveOrUpdateAddress(Address address);
    Mono<Void> deleteAddressById(ObjectId id);
    Mono<Void> deleteAddressByStreetNameAndHouseNumberAndCityAndCountryAnd(String streetName,
                                                                           String houseNumber,
                                                                           String city,
                                                                           String country);

    Mono<Void> deleteAddressByLatitudeAndLongitude(String latitude, String longitude);
    Mono<Void> deleteAll();
    Mono<Address> setAddressLatitudeAndLongitude(Address address);
}
