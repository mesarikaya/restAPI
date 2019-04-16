package com.mes.gotogether.bootstrap;


import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Role;
import com.mes.gotogether.domains.User;
import com.mes.gotogether.services.domain.AddressService;
import com.mes.gotogether.services.domain.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Bootstrap(UserService userService, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // FIRST sample user
        User user1 = new User();
        user1.setEmail("mesarikaya@gmail.com");
        user1.setFirstName("M.");
        user1.setMiddleName("E.");
        user1.setLastName("S.");
        user1.setOauthId("");
        user1.setActive(true);
        user1.setPassword(passwordEncoder.encode("admin"));
        user1.setRoles(Arrays.asList(new Role[] {Role.ADMIN, Role.USER, Role.GUEST}));

        Address address1 = new Address();
        address1.setStreetName("Parklaan");
        address1.setHouseNumber("103");
        address1.setCity("Sassenheim");
        address1.setCountry("The Netherlands");
        address1.setZipcode("2171 ED");

        // GET lat and lon for the address
        addressService.saveOrUpdateAddress(address1).block();
        user1.setAddress(address1);

        userService.saveOrUpdateUser(user1)
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization...")
                );

        // SECOND sample user
        User user2 = new User();
        user2.setEmail("");
        user2.setFirstName("Mustafa");
        user2.setMiddleName("E.");
        user2.setLastName("Sarikaya");
        user2.setOauthId("facebook");
        user2.setActive(true);
        user2.setPassword(passwordEncoder.encode("facebookSocial"));
        user2.setRoles(Arrays.asList(new Role[] {Role.USER, Role.GUEST}));

        Address address2 = new Address();
        address2.setStreetName("Parklaan");
        address2.setHouseNumber("103");
        address2.setCity("Sassenheim");
        address2.setCountry("The Netherlands");
        address2.setZipcode("2171ED");

        // GET lat and lon for the address
        addressService.saveOrUpdateAddress(address2).block();
        user2.setAddress(address1);

        userService.saveOrUpdateUser(user2)
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization 2...")
                );

        // System.out.println("User: "+ user2 + " is set");
    }
}
