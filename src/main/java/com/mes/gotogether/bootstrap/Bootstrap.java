package com.mes.gotogether.bootstrap;


import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Group;
import com.mes.gotogether.domains.LoginType;
import com.mes.gotogether.domains.Role;
import com.mes.gotogether.domains.User;
import com.mes.gotogether.services.domain.AddressService;
import com.mes.gotogether.services.domain.GroupService;
import com.mes.gotogether.services.domain.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;
    private final AddressService addressService;
    private final GroupService groupService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Bootstrap(UserService userService,
                     AddressService addressService,
                     GroupService groupService,
                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.addressService = addressService;
        this.groupService = groupService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Read test data file
        File testFile= ResourceUtils.getFile("classpath:data/TestData.csv");

        //File is found
        System.out.println("File Found : " + testFile.exists());

        // Read User Data
        List<UserDTO> userDTO = CsvReader.readUserDetails(testFile);
        
        // Read Address Data
        List<AddressDTO> addressDTO = CsvReader.readAddressDetails(testFile);
        
        // Read Group Data
        List<GroupDTO> groupDTO = CsvReader.readGroupDetails(testFile);

        HashMap<String, Group> groupNamesMap = new HashMap<>();
        for(GroupDTO gr: groupDTO){

            // Check if the group name is inside the hash set of names
            if(groupNamesMap.containsKey(gr.getName())){
                // Do nothing, a group is  already mapped to the name
            }else{
                // Create a group
                Group group = new Group();
                group.setId(new ObjectId());
                group.setName(gr.getName());
                group.setOriginSearchRadius(1);
                group.setDestinationSearchRadius(1);
                group.setActive(true);

                // Add the group to the hash map of group names
                groupNamesMap.put(gr.getName(), group);
            }
        }

        // Generate Fake Users, Addresses and Groups
        for(int itemLoc=0; itemLoc<userDTO.size()/50; itemLoc++){

            // Create user address and save to address schema
            Address userAddress = new Address(
                    new ObjectId(),
                    addressDTO.get(itemLoc).getStreetName(),
                    addressDTO.get(itemLoc).getHouseNumber(),
                    addressDTO.get(itemLoc).getCity(),
                    addressDTO.get(itemLoc).getZipcode(),
                    addressDTO.get(itemLoc).getState(),
                    addressDTO.get(itemLoc).getCountry(),
                    addressDTO.get(itemLoc).getLatitude(),
                    addressDTO.get(itemLoc).getLongitude(),
                    new Date());
            // Fake address data save without Nommatim Api location search
            addressService.saveFakeAddress(userAddress).block();

            // Create user
            User user = new User();
            user.setId(new ObjectId());
            user.setFirstName(userDTO.get(itemLoc).getFirstName());
            user.setMiddleName(userDTO.get(itemLoc).getMiddleName());
            user.setLastName(userDTO.get(itemLoc).getLastName());
            user.setEmail(userDTO.get(itemLoc).getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.get(itemLoc).getPassword()));
            user.setOauthId("");
            user.setLoginType(LoginType.LOCAL_LOGIN);
            user.setRoles(Arrays.asList(new Role[] {Role.ADMIN, Role.USER, Role.GUEST}));
            user.setActive(true);
            user.setVerified(true);
            user.setAddress(userAddress);

            // Find relevant group and add to the list of groups user has
            Group group = groupNamesMap.get(groupDTO.get(itemLoc).getName());
            if (group != null){
                if (group.getOriginAddress() == null){
                    group.setOriginAddress(userAddress);
                }

                if (group.getDestinationAddress() == null){
                    group.setDestinationAddress(userAddress);
                }

                /*// Add group to the user's membership list
                if (user.getGroups() != null) {
                    System.out.println("Adding group to the user membership list");
                    user.getGroups().add(group);
                }else{
                    System.out.println("Null USer membership");
                    HashSet<Group> userGroups = new HashSet<>();
                    userGroups.add(group);
                    user.setGroups(userGroups);
                }*/

                // Add user to the group members set
                if(group.getMembers()!=null) {
                	if (!group.getMembers().contains(user)) {
                        group.getMembers().add(user);
                    }else{
                        HashSet<User> usersInGroup = new HashSet<>();
                        usersInGroup.add(user);
                        group.setMembers(usersInGroup);
                    }
                }else{
                    HashSet<User> usersInGroup = new HashSet<>();
                    usersInGroup.add(user);
                    group.setMembers(usersInGroup);
                }
                	

                // Add user to the group owners set
                if(group.getOwners()!=null) {
                	if (!group.getOwners().contains(user)) {
                        group.getOwners().add(user);
                    }else{
                        HashSet<User> usersInGroup = new HashSet<>();
                        usersInGroup.add(user);
                        group.setOwners(usersInGroup);
                    }
                }else{
                    HashSet<User> usersInGroup = new HashSet<>();
                    usersInGroup.add(user);
                    group.setOwners(usersInGroup);
                }

                groupService.saveOrUpdate(group).block();
            }

            // Add the user to the data repository
            userService.saveOrUpdateUser(user).block();
        }
        
        System.out.println("FAKE data creation is successfull!");
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
