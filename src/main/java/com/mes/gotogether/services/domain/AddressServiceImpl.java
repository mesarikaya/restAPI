package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.NomatimOpenStreetMapQuery;
import com.mes.gotogether.repositories.domain.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private RestTemplate restTemplate;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, RestTemplate restTemplate) {
        this.addressRepository = addressRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<Address> findAddressById(ObjectId id) {
        return addressRepository.findById(id);
    }

    @Override
    public Mono<Address> saveOrUpdateAddress(Address address) {

        if (address != null){
            return addressRepository.findById(address.getId())
                    .flatMap(addressResult -> {
                        if (addressResult != null){
                            // TODO: Call https://nominatim.openstreetmap.org/search/Street, House Number, City
                            // TODO: Set the latitude and longitude
                            return this.setAddressLatitudeAndLongitude(addressResult)
                                    .flatMap(returnedAddress->addressRepository.save(returnedAddress)
                            );
                            // TODO: ADD ERORR OR SUCCESS HANDLERS
                        }else{
                            log.info("Creating a new User");
                            return this.createAddress(address);
                        }

                    });
        }else{
            // TODO: CREATE ERROR HANDLERS
            log.info("A Null address data is entered. Do not process!");
            return Mono.empty();
        }
    }

    @Override
    public Mono<Address> createAddress(Address address) {

        if (address != null) {
            this.setAddressLatitudeAndLongitude(address).flatMap(returnedAddress -> {
                return addressRepository.save(returnedAddress);
            });
            // TODO: ADD ERORR OR SUCCSES HANDLERS
        }else{
            // TODO: CREATE ERROR HANDLERS
            log.info("A Null address data is entered. Do not process!");
            return Mono.empty();
        }

        return addressRepository.save(address);
    }

    @Override
    public Mono<Void> deleteAddressById(ObjectId id) {
        return addressRepository.deleteById(id);
    }

    @Override
    public Mono<Address> setAddressLatitudeAndLongitude(Address address) {

        String baseUrl = "https://nominatim.openstreetmap.org/search/";
        String urlParameter = address.getStreetName() + " " + address.getHouseNumber() + ", " + address.getCity();
        String jsonResultFormat = "?format=json&addressdetails=1&limit=1";

        NomatimOpenStreetMapQuery[] queryResult = restTemplate.getForObject(
                baseUrl+urlParameter+jsonResultFormat,
                NomatimOpenStreetMapQuery[].class);
        log.info("**** QUERY ARRAY LENGTH: " + queryResult.length);
        log.info("******QUERY RESULT IS: " + queryResult[0].toString());

        if (queryResult.length>0){
            address.setLatitude(queryResult[0].getLatitude());
            address.setLongitude(queryResult[0].getLongitude());
        }

        return Mono.just(address);
    }
}
