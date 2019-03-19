package com.mes.gotogether.services;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.User;
import com.mes.gotogether.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private User existingUser;
    private User newUser;
    private User returnUser;
    private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        System.out.println("@Beforeeach is called!");

        // Create Existing Account
        existingUser = new User();
        existingUser.setEmail("abc@gmail.com");
        existingUser.setFirstName("First");
        existingUser.setMiddleName("Middle");
        existingUser.setLastName("Last");
        existingUser.setActive(true);
        existingUser.setMobileNumber("021234234");
        existingUser.setVerified(false);
        existingUser.setPermalink("abcgmailcom");

        Address address1 = new Address();
        address1.setStreetName("asda");
        address1.setHouseNumber("asd");
        address1.setCity("asdasd");
        address1.setCountry("asdasd");
        address1.setZipcode("sasd123");
        existingUser.setAddress(address1);
        // existingUser.setId((ObjectId) new ObjectIdGenerator().generate());

        // New User
        // Create Existing Account
        newUser = new User();
        newUser.setFirstName("NewFirst");
        newUser.setMiddleName("NewMiddle");
        newUser.setLastName("NewLast");
        newUser.setActive(true);
        newUser.setMobileNumber("0123123");
        newUser.setVerified(false);
        newUser.setOauthId("123123123");

        Address address2 = new Address();
        address2.setStreetName("ADress2");
        address2.setHouseNumber("asda");
        address2.setCity("asdsad");
        address2.setCountry("asd");
        address2.setZipcode("asdasd");
        newUser.setAddress(address2);
        // newUser.setId((ObjectId) new ObjectIdGenerator().generate());
        userService.saveOrUpdateUser(existingUser);
        userService.saveOrUpdateUser(newUser);

        System.out.println("Existing user: " + existingUser);
        System.out.println("New User: " + newUser);
    }


    public void setUp2(){

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUser(){
        User user = userService.saveOrUpdateUser(existingUser).block();
        Mono<User> userMono = userService.saveOrUpdateUser(existingUser);

        StepVerifier
                .create(userMono)
                .assertNext(u -> {
                    assertEquals(existingUser.getEmail(), u.getEmail());
                    assertEquals(existingUser.getLastName() , u.getLastName());
                })
                .expectComplete()
                .verify();

        assertEquals(user.getEmail(), existingUser.getEmail());
        assertEquals(user.getId(), existingUser.getId());
    }
}
