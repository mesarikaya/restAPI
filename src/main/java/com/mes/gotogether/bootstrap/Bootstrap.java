package com.mes.gotogether.bootstrap;


import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.User;
import com.mes.gotogether.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public Bootstrap(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        // FIRST sample user
        User user1 = new User();
        user1.setEmail("mes@gmail.com");
        user1.setFirstName("M.");
        user1.setMiddleName("E.");
        user1.setLastName("S.");
        user1.setActive(true);

        Address address1 = new Address();
        address1.setStreetName("Pasd");
        address1.setHouseNumber("103");
        address1.setCity("asdasd");
        address1.setCountry("The Netherlands");
        address1.setZipcode("1234rd");

        user1.setAddress(address1);

        userService.saveOrUpdateUser(user1).block();
        System.out.println("User: "+ user1.getId() + " is set");
    }
}
