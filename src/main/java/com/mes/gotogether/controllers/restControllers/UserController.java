package com.mes.gotogether.controllers.restControllers;

import com.mes.gotogether.domains.Group;
import com.mes.gotogether.domains.User;
import com.mes.gotogether.security.jwt.JWTUtil;
import com.mes.gotogether.security.service.SecurityUserLibraryUserDetailsService;
import com.mes.gotogether.services.domain.GroupService;
import com.mes.gotogether.services.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'GUEST')")
public class UserController {

    private final UserService userService;
    private final GroupService groupService;
    private final JWTUtil jwtUtil;
    private final SecurityUserLibraryUserDetailsService securityUserService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
    					  GroupService groupService,
                          JWTUtil jwtUtil, PasswordEncoder passwordEncoder,
                          SecurityUserLibraryUserDetailsService securityUserService) {
        this.userService = userService;
        this.groupService = groupService;
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
    
    @GetMapping("/groups/{origin}/{originRange}/{destination}/{destinationRange}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Group> getGroupsByOriginAndDestinationWithinRadius(
    		@PathVariable String origin, 
    		@PathVariable String originRange, 
    		@PathVariable String destination, 
    		@PathVariable String destinationRange)
    {
    	
    	return Flux.empty();
    	
    }
}
