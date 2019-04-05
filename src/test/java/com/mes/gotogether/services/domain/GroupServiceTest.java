package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Group;
import com.mes.gotogether.domains.NomatimOpenStreetMapQuery;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
    private Group existingAddress;
    private Group unchangedExistingGroup;
    private Group newAddress;
    private Group retrievedGroup1;
    private NomatimOpenStreetMapQuery[] existingAddressMapQuery;
    private NomatimOpenStreetMapQuery[] newAddressMapQuery;
    private NomatimOpenStreetMapQuery mapQuery1;
    private NomatimOpenStreetMapQuery mapQuery2;

    @BeforeEach
    public void setUp() {

        System.out.println("@BeforeEach is called!");
        MockitoAnnotations.initMocks(this);
        groupServiceImpl = new AddressServiceImpl(addressRepository, restTemplate);

        // Create Existing Address
        existingAddress = new Address();
        existingAddress.setStreetName("Martin Volderstraat");
        existingAddress.setHouseNumber("12");
        existingAddress.setCity("Utrecht");
        existingAddress.setCountry("The Netherlands");
        existingAddress.setZipcode("1234 EZ");
        existingAddress.setState("Utrecht");
        existingAddress.setId((ObjectId) new ObjectIdGenerator().generate());
        unchangedExistingAddress = new Address(existingAddress);

        mapQuery1 = new NomatimOpenStreetMapQuery();
        mapQuery1.setLatitude(11);
        mapQuery1.setLongitude(13);
        existingAddressMapQuery = new NomatimOpenStreetMapQuery[1];
        existingAddressMapQuery[0] = mapQuery1;

        // Create New Address
        newAddress = new Address();
        newAddress.setStreetName("Jan Janneman");
        newAddress.setHouseNumber("145");
        newAddress.setCity("Alkmaar");
        newAddress.setCountry("The Netherlands");
        newAddress.setZipcode("5421 AZ");
        newAddress.setState("Noord Holland");
        newAddress.setId((ObjectId) new ObjectIdGenerator().generate());

        mapQuery2 = new NomatimOpenStreetMapQuery();
        mapQuery2.setLatitude(11);
        mapQuery2.setLongitude(13);
        newAddressMapQuery = new NomatimOpenStreetMapQuery[1];
        newAddressMapQuery[0] = mapQuery2;

        // 1. Save the existing Address

        when(addressRepository.findById(existingAddress.getId())).thenReturn(Mono.just(existingAddress));
        when(restTemplate.getForObject(anyString(),eq(NomatimOpenStreetMapQuery[].class))).thenReturn(existingAddressMapQuery);
        when(addressRepository.save(existingAddress)).thenReturn(Mono.just(existingAddress));
        retrievedAddress1 = addressServiceImpl.saveOrUpdateAddress(existingAddress).log().flux().next().block();
    }

    @Test
    public void testMockCreation(){

        assertNotNull(existingAddress);
        assertNotNull(newAddress);
        assertNotNull(address);
        assertNotNull(addressRepository);
        assertNotNull(addressServiceImpl);
    }


    @Test
    public void saveAddress(){

        // 1. Save the existing Address
        when(addressRepository.findById(newAddress.getId())).thenReturn(Mono.empty());
        when(restTemplate.getForObject(anyString(),eq(NomatimOpenStreetMapQuery[].class))).thenReturn(newAddressMapQuery);
        when(addressRepository.save(newAddress)).thenReturn(Mono.just(newAddress));
        Address retrievedAddress = addressServiceImpl.saveOrUpdateAddress(newAddress).log().flux().next().block();
        assertEquals(newAddress.getId(), retrievedAddress.getId());
    }

