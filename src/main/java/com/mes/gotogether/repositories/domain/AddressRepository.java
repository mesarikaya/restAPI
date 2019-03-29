package com.mes.gotogether.repositories.domain;

import com.mes.gotogether.domains.Address;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends ReactiveMongoRepository<Address, ObjectId> {
}
