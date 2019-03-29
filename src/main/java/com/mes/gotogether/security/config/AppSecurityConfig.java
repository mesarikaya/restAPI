package com.mes.gotogether.security.config;

import com.mes.gotogether.domains.Role;
import com.mes.gotogether.services.domain.AddressService;
import com.mes.gotogether.services.domain.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class AppSecurityConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    /*
    @Bean
    @Primary
    public ReactiveUserDetailsService authentication(UserService userService) {

        System.out.println("Inside this*************8");
        String rawPassword = "1234test";
        User user = new User();
        user.setId((ObjectId) new ObjectIdGenerator().generate());
        user.setEmail("mesarikaya@gmail.com");
        user.setOauthId("facebooks");
        user.setPassword(rawPassword);
        user.setFirstName("M.");
        user.setMiddleName("E.");
        user.setLastName("S.");
        user.setActive(true);
        user.setRoles(new String[] {"ADMIN","USER"});

        Address address1 = new Address();
        address1.setStreetName("Parklaan");
        address1.setHouseNumber("103");
        address1.setCity("Sassenheim");
        address1.setCountry("The Netherlands");
        address1.setZipcode("2171 ED");

        // GET lat and lon for the address
        addressService.createAddress(address1);
        System.out.println("****Setting address****");
        user.setAddress(address1);

        System.out.println("UserDetails toString data: " + user.toUserDetails().toString());


        UserDetails userDetails =
                SpringSecurityUserDetails
                        .withUserDetails(user.toUserDetails())
                        .build();


                .withUsername(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
                                        // .withUserDetails(user.toUserDetails())
                                        // .passwordEncoder(User.PASSWORD_ENCODER::encode)
                                        // .build();


        return new MapReactiveUserDetailsService(userDetails);
    }
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /*
    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository users) {
        return (userId) -> userService.findByUserId(userId)
                .map(u -> {
                    log.debug("*****2----Creating the user details object from database*****");
                    return SpringSecurityUserDetails
                            .withUserDetails(u.toUserDetails())
                            .build();
                });
    }*/

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http
            .csrf().disable()
            .authorizeExchange()
            .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .matchers(EndpointRequest.to("health")).permitAll()
            .matchers(EndpointRequest.to("info")).permitAll()
            .matchers(EndpointRequest.toAnyEndpoint()).hasRole(Role.ADMIN.name())
            .pathMatchers(HttpMethod.POST, "/api/v1/users").hasRole(Role.ADMIN.name())
            .pathMatchers(HttpMethod.DELETE, "/api/v1/users").hasRole(Role.ADMIN.name())
            .anyExchange().authenticated()
            .and()
            .httpBasic().and().formLogin()
                .authenticationFailureHandler((exchange, exception) -> Mono.error(exception))
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/user"))
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler("/bye"));

        return http.build();
    }

    public ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create(uri));
        return successHandler;
    }
}
