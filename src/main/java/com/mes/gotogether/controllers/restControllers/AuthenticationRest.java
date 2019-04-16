package com.mes.gotogether.controllers.restControllers;

import com.mes.gotogether.security.domain.AuthRequest;
import com.mes.gotogether.security.domain.AuthResponse;
import com.mes.gotogether.security.domain.SecurityUserLibrary;
import com.mes.gotogether.security.jwt.JWTUtil;
import com.mes.gotogether.security.service.SecurityUserLibraryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/auth", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class AuthenticationRest {

    private JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private SecurityUserLibraryUserDetailsService securityUserService;

    @Autowired
    public AuthenticationRest(JWTUtil jwtUtil, PasswordEncoder passwordEncoder,
                              SecurityUserLibraryUserDetailsService securityUserService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.securityUserService = securityUserService;
    }

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<?>> login(AuthRequest ar, ServerHttpResponse serverHttpResponse) {

        System.out.println("Authorization: " + ar.getUsername() + "password: " + ar.getPassword() + "encoded: " +passwordEncoder.encode(ar.getPassword()));

        return securityUserService.findByUserId(ar.getUsername()).map((userDetails) -> {
            System.out.println("userdetails password: " + userDetails.getPassword());
            if (passwordEncoder.matches(ar.getPassword(), userDetails.getPassword())) {
                System.out.println("Authorized! YEAH!!!!");

                String token = jwtUtil.generateToken((SecurityUserLibrary) userDetails);

                ResponseCookie cookie = ResponseCookie.from("JWTCookie", token)
                                                        .maxAge(3600)
                                                        .build();

                serverHttpResponse.addCookie(cookie);

                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION,
                                String.join(" ","Bearer", token))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(new AuthResponse(token, userDetails.getUsername()));

                       // ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken((SecurityUserLibrary) userDetails)));
            } else {
                System.out.println("Returning unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }



}