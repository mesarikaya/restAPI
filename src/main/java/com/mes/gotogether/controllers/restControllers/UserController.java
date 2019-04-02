package com.mes.gotogether.controllers.restControllers;

import com.mes.gotogether.domains.User;
import com.mes.gotogether.services.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'GUEST')")
public class UserController {

    private final UserService userService;
    private final WebClient webClient;



    public UserController(UserService userService, WebClient webClient) {
        this.userService = userService;
        this.webClient = webClient;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<User>> getAllUsers(){
        System.out.println();
        return userService.findAllUsers();
    }

    @GetMapping("/userinfos")
    Mono<Map<String, Object>> userInfo(@AuthenticationPrincipal OAuth2User oauth2User) {


        return Mono.just(oauth2User.getAttributes());
    }

    @GetMapping("/login/oauth2/callback/google")
    public Mono<String> index(@AuthenticationPrincipal Mono<OAuth2User> oauth2User) {

        System.out.println("RUNNING THIS API CALLBACK");

        return oauth2User
                .map(OAuth2User::getName)
                .map(name -> String.format("Hi, %s", name));
    }

}
