package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.*;
import com.mes.gotogether.repositories.domain.GroupRepository;
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
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private Group group;
    @Mock
    private NomatimOpenStreetMapQuery nomatimOpenStreetMapQueries;
    private GroupServiceImpl groupServiceImpl;
    private Group existingGroup;
    private Group unchangedExistingGroup;
    private Group newGroup;
    private Group retrievedGroup1;

    @BeforeEach
    public void setUp() {

        System.out.println("@BeforeEach is called!");
        MockitoAnnotations.initMocks(this);
        groupServiceImpl = new GroupServiceImpl(groupRepository);

        // Create User 1
        User user1 = new User();
        user1.setEmail("abc@gmail.com");
        user1.setFirstName("First");
        user1.setMiddleName("Middle");
        user1.setLastName("Last");
        user1.setActive(true);
        user1.setMobileNumber("021234234");
        user1.setVerified(false);
        user1.setPermalink("abcgmailcom");
        user1.setId((ObjectId) new ObjectIdGenerator().generate());
        user1.setRoles(Arrays.asList(new Role[]{Role.ADMIN, Role.USER, Role.GUEST}));
        Address address1 = new Address();
        address1.setStreetName("asda");
        address1.setHouseNumber("asd");
        address1.setCity("asdasd");
        address1.setCountry("asdasd");
        address1.setZipcode("sasd123");
        address1.setLatitude(10);
        address1.setLongitude(14);
        address1.setId((ObjectId) new ObjectIdGenerator().generate());
        user1.setAddress(address1);
        user1.setId((ObjectId) new ObjectIdGenerator().generate());

        // Create User 2
        User user2 = new User();
        user2.setEmail("abc@gmail.com");
        user2.setFirstName("First");
        user2.setMiddleName("Middle");
        user2.setLastName("Last");
        user2.setActive(true);
        user2.setMobileNumber("021234234");
        user2.setVerified(false);
        user2.setPermalink("abcgmailcom");
        user2.setId((ObjectId) new ObjectIdGenerator().generate());
        user2.setRoles(Arrays.asList(new Role[]{Role.ADMIN, Role.USER, Role.GUEST}));
        Address address2 = new Address();
        address2.setStreetName("asda");
        address2.setHouseNumber("asd");
        address2.setCity("asdasd");
        address2.setCountry("asdasd");
        address2.setZipcode("sasd123");
        address2.setLatitude(11);
        address2.setLongitude(12);
        address2.setId((ObjectId) new ObjectIdGenerator().generate());
        user2.setAddress(address2);
        user2.setId((ObjectId) new ObjectIdGenerator().generate());

        // Create Existing Address
        existingGroup = new Group();
        existingGroup.setActive(true);
        existingGroup.setName("Group Test 1");
        HashSet<User> members = new HashSet<>();
        members.addAll(Arrays.asList(user1, user2));
        existingGroup.setMembers(members);
        existingGroup.setOriginAddress(address1);
        existingGroup.setOriginSearchRadius(3);
        existingGroup.setDestinationAddress(address2);
        existingGroup.setDestinationSearchRadius(3);
        HashSet<User> owners = new HashSet<>();
        owners.addAll(Arrays.asList(user1));
        existingGroup.setOwners(owners);
        existingGroup.setId((ObjectId) new ObjectIdGenerator().generate());

        unchangedExistingGroup = new Group(existingGroup);

        // New Group
        User user3 = new User();
        user3.setEmail("abcde@gmail.com");
        user3.setFirstName("Firsts");
        user3.setMiddleName("Middles");
        user3.setLastName("Lasts");
        user3.setActive(true);
        user3.setMobileNumber("0a21234234");
        user3.setVerified(false);
        user3.setPermalink("abcdegmailcom");
        user3.setId((ObjectId) new ObjectIdGenerator().generate());
        user3.setRoles(Arrays.asList(new Role[]{Role.ADMIN, Role.USER, Role.GUEST}));
        Address address3 = new Address();
        address3.setStreetName("asda");
        address3.setHouseNumber("asd");
        address3.setCity("asdasd");
        address3.setCountry("asdasd");
        address3.setZipcode("sasd123");
        address3.setLatitude(10);
        address3.setLongitude(14);
        address3.setId((ObjectId) new ObjectIdGenerator().generate());
        user3.setAddress(address3);
        user3.setId((ObjectId) new ObjectIdGenerator().generate());

        // Create User 2
        User user4 = new User();
        user4.setEmail("abcmn@gmail.com");
        user4.setFirstName("Firstm");
        user4.setMiddleName("Middlem");
        user4.setLastName("Lastm");
        user4.setActive(true);
        user4.setMobileNumber("021234234asda");
        user4.setVerified(false);
        user4.setPermalink("abcmngmailcom");
        user4.setId((ObjectId) new ObjectIdGenerator().generate());
        user4.setRoles(Arrays.asList(new Role[]{Role.ADMIN, Role.USER, Role.GUEST}));
        Address address4 = new Address();
        address4.setStreetName("asda");
        address4.setHouseNumber("asd");
        address4.setCity("asdasd");
        address4.setCountry("asdasd");
        address4.setZipcode("sasd123");
        address4.setLatitude(11);
        address4.setLongitude(12);
        address4.setId((ObjectId) new ObjectIdGenerator().generate());
        user4.setAddress(address2);
        user4.setId((ObjectId) new ObjectIdGenerator().generate());

        // Create New Group
        newGroup = new Group();
        newGroup.setActive(true);
        newGroup.setName("Group Test 1");
        HashSet<User> members2 = new HashSet<>();
        members2.addAll(Arrays.asList(user3, user4));
        newGroup.setMembers(members);
        newGroup.setOriginAddress(address1);
        newGroup.setOriginSearchRadius(3);
        newGroup.setDestinationAddress(address2);
        newGroup.setDestinationSearchRadius(3);
        HashSet<User> owners2 = new HashSet<>();
        owners2.addAll(Arrays.asList(user3));
        newGroup.setOwners(owners);
        newGroup.setId((ObjectId) new ObjectIdGenerator().generate());

        // 1. Save the existing Address
        when(groupRepository.findById(existingGroup.getId())).thenReturn(Mono.just(existingGroup));
        when(groupRepository.save(existingGroup)).thenReturn(Mono.just(existingGroup));
        retrievedGroup1 = groupServiceImpl.saveOrUpdate(existingGroup).log().flux().next().block();
    }

    @Test
    public void testMockCreation() {

        assertNotNull(existingGroup);
        assertNotNull(newGroup);
        assertNotNull(group);
        assertNotNull(groupRepository);
        assertNotNull(groupServiceImpl);
    }


    @Test
    public void saveGroup() {

        // 1. Save the existing Address
        when(groupRepository.findById(newGroup.getId())).thenReturn(Mono.empty());
        when(groupRepository.save(newGroup)).thenReturn(Mono.just(newGroup));
        Group retrievedGroup = groupServiceImpl.saveOrUpdate(newGroup).log().flux().next().block();
        assertEquals(newGroup.getId(), retrievedGroup.getId());
    }

    @Test
    public void saveNullGroup(){

        // 1. Save the null Address
        Mono<Group> retrievedGroup = groupServiceImpl.saveOrUpdate(null);
        assertEquals(Mono.empty(), retrievedGroup);
    }

    @Test
    public void updateGroup(){

        // Change existing Address
        existingGroup.setName("Group Test changed Name");
        existingGroup.setActive(false);

        // 1. Save the existing Address
        when(groupRepository.findById(existingGroup.getId())).thenReturn(Mono.just(existingGroup));
        when(groupRepository.save(existingGroup)).thenReturn(Mono.just(existingGroup));
        Group retrievedGroup = groupServiceImpl.saveOrUpdate(existingGroup).log().flux().next().block();

        // Check if they are the same object
        assertEquals(unchangedExistingGroup.getId(), retrievedGroup.getId());
    }

    @Test
    public void findGroupById(){

        // Search the Address by _id
        Group retrievedGroup = groupServiceImpl.findById(existingGroup.getId()).log().flux().next().block();
        assertEquals(existingGroup, retrievedGroup);
    }


    @Test
    public void findAddressByNullId(){

        // Search the Address by Null Address id
        Group retrievedGroup = groupServiceImpl.findById(null).log().flux().next().block();
        assertEquals(null, retrievedGroup);
    }

}

