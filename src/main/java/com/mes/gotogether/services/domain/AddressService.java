package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface AddressService {

    Mono<Address> findAddressById(ObjectId id);
    Mono<Address> saveOrUpdateAddress(Address address);
    Mono<Address> createAddress(Address address);
    Mono<Void> deleteAddressById(ObjectId id);
    Mono<Address> setAddressLatitudeAndLongitude(Address address);
}
