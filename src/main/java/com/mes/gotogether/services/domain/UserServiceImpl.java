package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.User;
import com.mes.gotogether.repositories.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<List<User>> findAllUsers() {
        return userRepository.findAll().collectList();
    }

    @Override
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public Mono<User> findByUserId(String userId) {

        // Check if user exists
        return userRepository.findByUserId(userId);
    }

    @Override
    public Mono<User> createUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public Mono<User> saveOrUpdateUser(User user) {

        // Check if new user is null
        if(user != null){
            System.out.println("REQUESTIG SAVE OR UPDATE with  user: " + user);
            // Check if the user exists (By email and oauthId)
            // Check if user exists, if so, update. Otherwise create
            return userRepository.findByUserId(user.getUserId())
                    .flatMap(userInDb -> {
                        System.out.println("user in db is: " + userInDb);
                        if (userInDb != null){
                            System.out.println("Update the user");
                            user.setId(userInDb.getId());
                            System.out.println("USER in repository: " + userInDb);
                            return userRepository.save(user);
                        }else{
                            System.out.println("Creating a new User 1");
                            return this.createUser(user);
                        }
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        System.out.println("Creating a new User 2");
                        return this.createUser(user);
                    }));

            // TODO: CREATE SUCCESS HANDLER AND CONNECT ON SUCCSES CASE
            // TODO: CREATE AN ERROR HANDLER AND CONNECT ON ERROR CASE
        }else{

            // TODO: CREATE ERROR HANDLERS
            log.info("A Null user data is entered. Do not process!");
            return Mono.empty();
        }
    }

    @Override
    public Mono<Void> deleteUserById(ObjectId id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }

    @Override
    public Mono<User> setPassword(User user, String newPassword) {

        // Find if user exists
        if (user != null){
            // Get the user by userId
            return userRepository.findByUserId(user.getOauthId())
                    .flatMap(returnedUser -> {
                        // Reset the password and save
                        returnedUser.setPassword(newPassword);
                        return userRepository.save(returnedUser);
                    });
            // TODO: ADD SUCCESS AND ERROR MESSAGES
        }else{

            // TODO: CREATE ERROR HANDLERS
            log.info("A Null user data is entered. Do not process!");
            return Mono.empty();
        }
    }
}
