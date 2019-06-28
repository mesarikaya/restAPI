package com.mes.gotogether.controllers.restControllers;

import com.mes.gotogether.domains.User;
import com.mes.gotogether.security.jwt.JWTUtil;
import com.mes.gotogether.security.service.SecurityUserLibraryUserDetailsService;
import com.mes.gotogether.services.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'GUEST')")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final SecurityUserLibraryUserDetailsService securityUserService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          JWTUtil jwtUtil, PasswordEncoder passwordEncoder,
                          SecurityUserLibraryUserDetailsService securityUserService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.securityUserService = securityUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<User>> getAllUsers(){
        System.out.println();
        return userService.findAllUsers();
    }

}
