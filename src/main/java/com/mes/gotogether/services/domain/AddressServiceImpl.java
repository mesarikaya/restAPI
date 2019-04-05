package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.NomatimOpenStreetMapQuery;
import com.mes.gotogether.repositories.domain.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private RestTemplate restTemplate;

    // FIND METHODS
    public AddressServiceImpl(AddressRepository addressRepository, RestTemplate restTemplate) {
        this.addressRepository = addressRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<Address> findAddressById(ObjectId id) {

        if (ObjectUtils.isEmpty(id)) return Mono.empty();

        return addressRepository.findById(id);
    }

    @Override
    public Mono<Address> findAddressByStreetNameAndHouseNumberAndCityAndCountry(String streetName,
                                                                                String houseNumber,
                                                                                String city,
                                                                                String country) {

        // If null, do nothing
        if (ObjectUtils.isEmpty(streetName)
                || ObjectUtils.isEmpty(houseNumber)
                || ObjectUtils.isEmpty(city)
                || ObjectUtils.isEmpty(country)) return Mono.empty();

        return addressRepository.findFirstByAddressByStreetNameAndHouseNumberAndCityAndCountryOrderByLastModifiedDesc(
                streetName,
                houseNumber,
                city,
                country);
    }

    @Override
    public Flux<Address> findAddressByLatitudeAndLongitudeAnd(Double latitude, Double longitude) {

        // If null, do nothing
        if (ObjectUtils.isEmpty(latitude) || ObjectUtils.isEmpty(longitude)) return Flux.empty();

        return addressRepository.findFirst10AddressByLatitudeAndLongitudeOrderByLastModifiedDesc(latitude, longitude);
    }

    @Override
    public Flux<Address> findAll() {
        return addressRepository.findAll();
    }

    // SAVE OR UPDATE METHODS
    @Override
    public Mono<Address> saveOrUpdateAddress(Address address) {

        if (!ObjectUtils.isEmpty(address)){
            return this.findAddressById(address.getId())
                    .flatMap(addressResult -> {
                            addressResult.setId(address.getId());
                            return this.setAddressLatitudeAndLongitude(addressResult)
                                    .flatMap(returnedAddress->addressRepository.save(returnedAddress))
                                    .switchIfEmpty(Mono.defer(()-> Mono.empty()));
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        log.debug("Creating a new Address");
                        return this.setAddressLatitudeAndLongitude(address)
                                .flatMap(returnedAddress->addressRepository.save(returnedAddress))
                                .switchIfEmpty(Mono.defer(()-> Mono.empty()));
                    }));
            // TODO: ADD ERORR OR SUCCESS HANDLERS*/
        }else{
            // TODO: CREATE ERROR HANDLERS
            log.info("A Null address data is entered. Do not process!");
            return Mono.empty();
        }
    }

    // DELETE METHODS
    @Override
    public Mono<Void> deleteAddressById(ObjectId id) {

        if (ObjectUtils.isEmpty(id)) return Mono.empty();

        return addressRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAddressByStreetNameAndHouseNumberAndCityAndCountryAnd(String streetName, String houseNumber, String city, String country) {

        // If any is null, do nothing
        if (ObjectUtils.isEmpty(streetName)
                || ObjectUtils.isEmpty(houseNumber)
                || ObjectUtils.isEmpty(city)
                || ObjectUtils.isEmpty(country)) return Mono.empty();

        return addressRepository.deleteAddressByStreetNameAndHouseNumberAndCityAndCountry(
                streetName,
                houseNumber,
                city,
                country);
    }

    @Override
    public Mono<Void> deleteAddressByLatitudeAndLongitude(Double latitude, Double longitude) {

        // If any is null, do nothing
        if (ObjectUtils.isEmpty(latitude) || ObjectUtils.isEmpty(longitude)) return Mono.empty();

        return addressRepository.deleteAddressByLatitudeAndLongitude(latitude, longitude);
    }

    @Override
    public Mono<Void> deleteAll() {
        return addressRepository.deleteAll();
    }

    @Override
    public Mono<Address> setAddressLatitudeAndLongitude(Address address) {

        // If null, do nothing
        if (ObjectUtils.isEmpty(address)) return Mono.empty();

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
