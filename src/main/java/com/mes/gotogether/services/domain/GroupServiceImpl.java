package com.mes.gotogether.services.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.*;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Group;
import com.mes.gotogether.repositories.domain.GroupRepository;
import com.mes.gotogether.services.externalconnections.GeoLocationService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService{

    private GroupRepository groupRepository;
    private GeoLocationService geoLocationService;
    
    public GroupServiceImpl(GroupRepository groupRepository, GeoLocationService geoLocationService) {
        this.groupRepository = groupRepository;
        this.geoLocationService = geoLocationService;
    }

    // FIND METHODS
    @Override
    public Mono<Group> findById(ObjectId id) {

        if (isEmpty(id)) return Mono.empty();

        return groupRepository.findById(id);
    }

    @Override
    public Flux<Group> findGroupsByName(String groupName) {
        if (isEmpty(groupName)) return Flux.empty();
        System.out.println("Searching the group by name");
        return groupRepository.findGroupsByName(groupName);
    }

    @Override
    public Flux<Group> findGroupsByOriginAddress(Address originAddress) {

        if (isEmpty(originAddress)) return Flux.empty();

        return groupRepository.findGroupsByOriginAddress(originAddress);
    }

    @Override
    public Flux<Group> findGroupsByDestinationAddress(Address destinationAddress) {

        if (isEmpty(destinationAddress)) return Flux.empty();

        return groupRepository.findGroupsByDestinationAddress(destinationAddress);
    }

    @Override
    public Flux<Group> findGroupsByOriginAndDestinationAddress(Address originAddress, Address destinationAddress) {

        if (isEmpty(originAddress) || isEmpty(destinationAddress)) return Flux.empty();

        return groupRepository.findGroupsByOriginAddressAndDestinationAddress(originAddress, destinationAddress);
    }

    @Override
	public Flux<Group> findGroupsByOriginAndDestinationAddress(String originAddress, String destinationAddress,
			double originRadius, double destinationRadius) {
    	
    	log.info("Sending latitude and longitude");
    	Optional<Double[]> originLatitudeAndLongitude = geoLocationService.getAddressLongitudeAndLatitude(originAddress);
    	Optional<Double[]> destinationLatitudeAndLongitude = geoLocationService.getAddressLongitudeAndLatitude(destinationAddress);
    	
    	if (originLatitudeAndLongitude.isEmpty() || destinationLatitudeAndLongitude.isEmpty()) {
    		log.info("Sending flux empty due to empty origin or destination latitude");
    		return Flux.empty();
    	}
    	
		Double[] originGeoLocations = originLatitudeAndLongitude.get();
		Double[] destinationGeoLocations = destinationLatitudeAndLongitude.get();
		log.info("Searching the groups within origin: " + originGeoLocations + " and destination: " + destinationGeoLocations);
		
		return this.findGroupsByOriginAndDestinationWithinRadius(
	            originGeoLocations[0], originGeoLocations[1], originRadius,
	            destinationGeoLocations[0], destinationGeoLocations[1], destinationRadius); 	
   }

	@Override
    public Flux<Group> findGroupsByOriginAndDestinationGeoLocationDetails(
            Double originLatitude, Double originLongitude,
            Double destinationLatitude, Double destinationLongitude) {

        if (isEmpty(originLatitude)
                || isEmpty(originLongitude)
                || isEmpty(destinationLatitude)
                || isEmpty(destinationLongitude)) return Flux.empty();

        return groupRepository.findGroupsByOriginAndDestinationGeoLocationDetails(
                originLatitude, originLongitude,
                destinationLatitude, destinationLongitude);
    }

    @Override
    public Flux<Group> findGroupsByOriginWithinThresholds(
            Double originLatMin, Double originLatMax,
            Double originLongMin, Double originLongMax) {

        if (isEmpty(originLatMin) || isEmpty(originLatMax)
                || isEmpty(originLongMin) || isEmpty(originLongMax)) return Flux.empty();


        return groupRepository.findGroupsByOriginWithinThresholds(
                originLatMin, originLatMax,
                originLongMin, originLongMax);
    }

    @Override
    public Flux<Group> findGroupsByDestinationWithinThresholds(
            Double destLatMin, Double destLatMax,
            Double destLongMin, Double destLongMax) {

        if (isEmpty(destLatMin) || isEmpty(destLatMax)
                || isEmpty(destLongMin) || isEmpty(destLongMax)) return Flux.empty();

        return groupRepository.findGroupsByDestinationWithinThresholds(
                destLatMin, destLatMax,
                destLongMin, destLongMax);
    }

    @Override
    public Flux<Group> findGroupsByOriginAndDestinationWithinThresholds(
            Double originLatMin, Double originLatMax,
            Double originLongMin, Double originLongMax,
            Double destLatMin, Double destLatMax,
            Double destLongMin, Double destLongMax) {

        if (isEmpty(originLatMin) || isEmpty(originLatMax)
                || isEmpty(originLongMin) || isEmpty(originLongMax)
                || isEmpty(destLatMin) || isEmpty(destLatMax)
                || isEmpty(destLongMin) || isEmpty(destLongMax)) return Flux.empty();

        log.info("Requested mongo query parameters\n" + 
        "Origin min latitude: " + originLatMin + "\n" +
        "Origin max latitude: " + originLatMax + "\n" +
        "Origin min longitude: " + originLongMin + "\n" +
        "Origin max longitude: " + originLongMax + "\n" +
        "Destination min latitude: " + destLatMin + "\n" +
        "Destination max latitude: " + destLatMax + "\n" +
        "Destination min longitude: " + destLongMin + "\n" +
        "Destination max longitude: " + destLongMax + "\n");
        return groupRepository.findGroupsByOriginAndDestinationWithinThresholds(
                originLatMin, originLatMax,
                originLongMin, originLongMax,
                destLatMin, destLatMax,
                destLongMin, destLongMax);
    }

    public Flux<Group> findGroupsByOriginWithinRadius(
            Double originLat, Double originLong, Double originRadius){

        if (isEmpty(originLat) || isEmpty(originLong)
                || isEmpty(originRadius) || originRadius<0 ) return Flux.empty();

        GeoLocationThreshold originThresholds = new GeoLocationThreshold(originLat, originLong, originRadius);

        return this.findGroupsByOriginWithinThresholds(
                originThresholds.getLatMin(), originThresholds.getLatMax(),
                originThresholds.getLongMin(), originThresholds.getLongMax());
    }

    public Flux<Group> findGroupsByDestinationWithinRadius(
            Double destLat, Double destLong, Double destRadius){

        if (isEmpty(destLat) || isEmpty(destLong)
                || isEmpty(destRadius) || destRadius < 0) return Flux.empty();

        GeoLocationThreshold destThresholds = new GeoLocationThreshold(destLat, destLong, destRadius);

        return this.findGroupsByDestinationWithinThresholds(
                destThresholds.getLatMin(), destThresholds.getLatMax(),
                destThresholds.getLongMin(), destThresholds.getLongMax());
    }

    public Flux<Group> findGroupsByOriginAndDestinationWithinRadius(
            Double originLat, Double originLong, Double originRadius,
            Double destLat, Double destLong, Double destRadius){

        if (isEmpty(originLat) || isEmpty(originLong)
                || isEmpty(destLat) || isEmpty(destLong)
                || isEmpty(originRadius) || isEmpty(destRadius)
                || originRadius<0 || destRadius < 0) return Flux.empty();

        GeoLocationThreshold originThresholds = new GeoLocationThreshold(originLat, originLong, originRadius);
        GeoLocationThreshold destThresholds = new GeoLocationThreshold(destLat, destLong, destRadius);

        return this.findGroupsByOriginAndDestinationWithinThresholds(
                originThresholds.getLatMin(), originThresholds.getLatMax(),
                originThresholds.getLongMin(), originThresholds.getLongMax(),
                destThresholds.getLatMin(), destThresholds.getLatMax(),
                destThresholds.getLongMin(), destThresholds.getLongMax());
    }

    @Override
    public Flux<Group> findAll() {
        return groupRepository.findAll();
    }

    // SAVE OR UPDATE
    @Override
    public Mono<Group> saveOrUpdate(Group group) {

        if (!isEmpty(group)){
            return groupRepository.findById(group.getId())
                    .flatMap(groupInDb -> {
                        log.debug("group in db is: " + groupInDb);
                        log.debug("Update the group");
                        group.setId(groupInDb.getId());
                        log.debug("Group in repository: " + groupInDb);
                        return groupRepository.save(group);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        log.debug("Creating a new Group");
                        return groupRepository.save(group);
                    }));
            // TODO: ADD ERROR OR SUCCESS HANDLERS*/
        }else{
            // TODO: CREATE ERROR HANDLERS
            System.out.println("Could not save the GROUP");
            log.debug("A Null group data is entered. Do not process!");
            return Mono.empty();
        }
    }

    // SAVE Fake group


    @Override
    public Mono<Group> saveFakeGroup(Group group) {
        return groupRepository.save(group);
    }

    // DELETE
    @Override
    public Mono<Void> deleteById(ObjectId id) {

        if (isEmpty(id)) return Mono.empty();

        return groupRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {

        return groupRepository.deleteAll();
    }
}
