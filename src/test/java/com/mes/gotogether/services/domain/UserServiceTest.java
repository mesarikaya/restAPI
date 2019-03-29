package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.User;
import com.mes.gotogether.repositories.domain.UserRepository;
import org.bson.codecs.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private Mono<User> monoUserMock;
    private UserServiceImpl userServiceImpl;
    private User existingUser;
    private User newUser;
    private User retrievedUser1;
    private User retrievedUser2;
    // private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeEach
    public void setUp() {

        System.out.println("@BeforeEach is called!");
        MockitoAnnotations.initMocks(this);

        userServiceImpl = new UserServiceImpl(userRepository);

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
        existingUser.setId((ObjectId) new ObjectIdGenerator().generate());
        existingUser.setRoles(new String[] {"ADMIN", "USER", "GUEST"});
        Address address1 = new Address();
        address1.setStreetName("asda");
        address1.setHouseNumber("asd");
        address1.setCity("asdasd");
        address1.setCountry("asdasd");
        address1.setZipcode("sasd123");
        existingUser.setAddress(address1);
        existingUser.setId((ObjectId) new ObjectIdGenerator().generate());

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
        newUser.setId((ObjectId) new ObjectIdGenerator().generate());
        newUser.setRoles(new String[] {"USER","GUEST"});
        Address address2 = new Address();
        address2.setStreetName("ADress2");
        address2.setHouseNumber("asda");
        address2.setCity("asdsad");
        address2.setCountry("asd");
        address2.setZipcode("asdasd");
        newUser.setAddress(address2);

        // 1. Save the existing user
        when(userRepository.findByUserId(existingUser.getUserId())).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));
        retrievedUser1 = userServiceImpl.saveOrUpdateUser(existingUser).log().flux().next().block();
    }

    @Test
    public void testMockCreation(){

        assertNotNull(existingUser);
        assertNotNull(newUser);
        assertNotNull(user);
        assertNotNull(userRepository);
        assertNotNull(userServiceImpl);
    }

    @Test
    public void saveUser(){

        when(userRepository.findByUserId(anyString())).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));
        User retrievedUser = userServiceImpl.saveOrUpdateUser(existingUser).log().flux().next().block();
        assertEquals(retrievedUser.getUserId(), existingUser.getUserId());
    }

    @Test
    public void saveNullUser(){

        Mono<User> retrievedUser = userServiceImpl.saveOrUpdateUser(null);
        assertEquals(Mono.empty(),retrievedUser);
    }

    @Test
    public void updateUser(){

        // Change existing user name
        existingUser.setFirstName("Ergin");
        existingUser.setEmail("mesarikaya@gmail.com");
        when(userRepository.findByUserId(existingUser.getUserId())).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));
        User retrievedUser2 = userServiceImpl.saveOrUpdateUser(existingUser).log().flux().next().block();
        // Check if they are the same object
        assertEquals(retrievedUser1.getId(), retrievedUser2.getId());
    }


    @Test
    public void findUserById(){

        // Search he user by _id
        when(userRepository.findById(existingUser.getId())).thenReturn(Mono.just(existingUser));
        User retrievedUser = userServiceImpl.findUserById(existingUser.getId()).log().flux().next().block();
        assertEquals(existingUser.getId(), retrievedUser.getId());
    }

    @Test
    public void findUserByUserId(){

        // Search he user by _id
        when(userRepository.findByUserId(existingUser.getUserId())).thenReturn(Mono.just(existingUser));
        User retrievedUser = userServiceImpl.findByUserId(existingUser.getUserId()).log().flux().next().block();
        assertEquals(existingUser.getId(), retrievedUser.getId());
    }

    @Test
    public void findAllUsers(){

        // Search all existing users
        when(userRepository.findAll()).thenReturn(Flux.just(existingUser));
        List<User> retrievedUsers = userServiceImpl.findAllUsers().log().flux().next().block();
        assertNotNull(retrievedUsers);
        assertEquals(existingUser, retrievedUsers.get(0));
    }

    @Test
    public void deleteUserById(){

        // Delete user by _id
        when(userRepository.deleteById(any(ObjectId.class))).thenReturn(Mono.empty());
        userServiceImpl.deleteUserById(existingUser.getId()).log().flux().next().block();
        verify(userRepository, times(1)).deleteById(existingUser.getId());
    }

    @Test
    public void deleteAllUsers(){

        // Verify if all users are deleted
        when(userRepository.deleteAll()).thenReturn(Mono.empty());
        userServiceImpl.deleteAll();
        verify(userRepository, times(1)).deleteAll();

        // Check if the existing user still exists
        when(userRepository.findById(any(ObjectId.class))).thenReturn(Mono.empty());
        Mono<User> retrievedUser = userServiceImpl.findUserById(existingUser.getId());
        assertEquals(Mono.empty(),retrievedUser);
    }
}
