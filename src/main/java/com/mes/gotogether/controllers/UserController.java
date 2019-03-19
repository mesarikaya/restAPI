package com.mes.gotogether.controllers;

import com.mes.gotogether.domains.User;
import com.mes.gotogether.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<User>> getAllUsers(){
        System.out.println();
        return userService.findAllUsers();
    }
}
