package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.User;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<User> findUserById(ObjectId id);
    Mono<List<User>> findAllUsers();
    Mono<User> findByUserId(String userId);
    Mono<User> createUser(User user);
    Mono<User> saveOrUpdateUser(User user);
    Mono<Void> deleteUserById(ObjectId id);
    Mono<Void> deleteAll();
}
