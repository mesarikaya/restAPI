package com.mes.gotogether.repositories;

import com.mes.gotogether.domains.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {
}
