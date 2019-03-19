package com.mes.gotogether.repositories;

import com.mes.gotogether.domains.Group;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GroupRepository extends ReactiveMongoRepository<Group, String> {
}
