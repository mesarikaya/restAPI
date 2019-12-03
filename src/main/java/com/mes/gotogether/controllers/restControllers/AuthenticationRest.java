package com.mes.gotogether.controllers.restControllers;

import com.mes.gotogether.security.domain.AuthRequest;
import com.mes.gotogether.security.domain.AuthResponse;
import com.mes.gotogether.security.domain.SecurityUserLibrary;
import com.mes.gotogether.security.jwt.JWTUtil;
import com.mes.gotogether.security.service.SecurityUserLibraryUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
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
    @CrossOrigin(
            origins = "http://localhost:3000",
            maxAge = 18000,
            allowCredentials = "true")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar,
                                         ServerHttpResponse serverHttpResponse
                                         ) {

    	log.info("Authorization request is: " + ar );
    	log.info("Authorization: " + ar.getUsername() +
                "password: " + ar.getPassword() +
                "encoded: " +passwordEncoder.encode(ar.getPassword()));
        return securityUserService.findByUserId(ar.getUsername().split("@")[0]).map((userDetails) -> {
            System.out.println("userdetails password: " + userDetails.getPassword());
            if (passwordEncoder.matches(ar.getPassword(), userDetails.getPassword())) {
                log.info("Authorized! YEAH!!!!");

                String token = jwtUtil.generateToken((SecurityUserLibrary) userDetails);

                ResponseCookie cookie = ResponseCookie
                                                .from("System", token)
                                                .sameSite("Strict")
                                                .path("/")
                                                .maxAge(3000)
                                                .httpOnly(true)
                                                .build();
                serverHttpResponse.addCookie(cookie);

                log.info("Server response: " + serverHttpResponse.getCookies().toSingleValueMap().values());
                
                return ResponseEntity
                        .ok()
                        //.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(new AuthResponse(token, userDetails.getUsername()));
                        //.build();

                //serverHttpResponse.getHeaders().add(HttpHeaders.SET_COOKIE,"test: " + cookie.toString());
                //System.out.println("Server Http response: " + serverHttpResponse.getCookies().toString());
//                        .header(HttpHeaders.AUTHORIZATION,
//                                String.join(" ","Bearer", token))
                /*return ResponseEntity
                        .ok()
                        //.header(HttpHeaders.SET_COOKIE, serverHttpResponse.getCookies().toSingleValueMap().values().toString())
                        .header(HttpHeaders.SET_COOKIE,
                                cookie.getName() + "=" + cookie.getValue() + ";"
                                        + "httpOnly="+ cookie.isHttpOnly() + ";"
                                        + "expires=" + cookie.getMaxAge() + ";"
                                        + "sameSite=" + cookie.getSameSite())
                        //.contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(new AuthResponse(token, userDetails.getUsername()));*/

                // ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken((SecurityUserLibrary) userDetails)));
            } else {
                System.out.println("Returning unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }



}

/*
authRequestMono.flatMap(
            ar ->{
                System.out.println("Authorization request is: " + ar );
                System.out.println("Authorization: " + ar.getUsername() +
                        "password: " + ar.getPassword() +
                        "encoded: " +passwordEncoder.encode(ar.getPassword()));
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
            }).onErrorReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());


 */