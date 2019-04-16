package com.mes.gotogether.services.domain;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.Group;
import com.mes.gotogether.repositories.domain.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService{

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    // FIND METHODS
    @Override
    public Mono<Group> findById(ObjectId id) {

        if (ObjectUtils.isEmpty(id)) return Mono.empty();

        return groupRepository.findById(id);
    }

    @Override
    public Flux<Group> findGroupsByOriginAddress(Address originAddress) {

        if (ObjectUtils.isEmpty(originAddress)) return Flux.empty();

        return groupRepository.findGroupsByOriginAddress(originAddress);
    }

    @Override
    public Flux<Group> findGroupsByDestinationAddress(Address destinationAddress) {

        if (ObjectUtils.isEmpty(destinationAddress)) return Flux.empty();

        return groupRepository.findGroupsByDestinationAddress(destinationAddress);
    }

    @Override
    public Flux<Group> findGroupsByOriginAndDestinationAddress(Address originAddress, Address destinationAddress) {

        if (ObjectUtils.isEmpty(originAddress) || ObjectUtils.isEmpty(destinationAddress)) return Flux.empty();

        return groupRepository.findGroupsByOriginAddressAndDestinationAddress(originAddress, destinationAddress);
    }

    @Override
    public Flux<Group> findGroupsByOriginAndDestinationGeoLocationDetails(
            Double originLatitude, Double originLongitude,
            Double destinationLatitude, Double destinationLongitude) {

        if (ObjectUtils.isEmpty(originLatitude)
                || ObjectUtils.isEmpty(originLongitude)
                || ObjectUtils.isEmpty(destinationLatitude)
                || ObjectUtils.isEmpty(destinationLongitude)) return Flux.empty();

        return groupRepository.findGroupsByOriginAndDestinationGeoLocationDetails(
                originLatitude, originLongitude,
                destinationLatitude, destinationLongitude);
    }

    @Override
    public Flux<Group> findGroupsByOriginWithinThresholds(
            Double originLatMin, Double originLatMax,
            Double originLongMin, Double originLongMax) {

        if (ObjectUtils.isEmpty(originLatMin) || ObjectUtils.isEmpty(originLatMax)
                || ObjectUtils.isEmpty(originLongMin) || ObjectUtils.isEmpty(originLongMax)) return Flux.empty();


        return groupRepository.findGroupsByOriginWithinThresholds(
                originLatMin, originLatMax,
                originLongMin, originLongMax);
    }

    @Override
    public Flux<Group> findGroupsByDestinationWithinThresholds(
            Double destLatMin, Double destLatMax,
            Double destLongMin, Double destLongMax) {

        if (ObjectUtils.isEmpty(destLatMin) || ObjectUtils.isEmpty(destLatMax)
                || ObjectUtils.isEmpty(destLongMin) || ObjectUtils.isEmpty(destLongMax)) return Flux.empty();

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

        if (ObjectUtils.isEmpty(originLatMin) || ObjectUtils.isEmpty(originLatMax)
                || ObjectUtils.isEmpty(originLongMin) || ObjectUtils.isEmpty(originLongMax)
                || ObjectUtils.isEmpty(destLatMin) || ObjectUtils.isEmpty(destLatMax)
                || ObjectUtils.isEmpty(destLongMin) || ObjectUtils.isEmpty(destLongMax)) return Flux.empty();

        return groupRepository.findGroupsByOriginAndDestinationWithinThresholds(
                originLatMin, originLatMax,
                originLongMin, originLongMax,
                destLatMin, destLatMax,
                destLongMin, destLongMax);
    }

    public Flux<Group> findGroupsByOriginWithinRadius(
            Double originLat, Double originLong, Double originRadius){

        if (ObjectUtils.isEmpty(originLat) || ObjectUtils.isEmpty(originLong)
                || ObjectUtils.isEmpty(originRadius) || originRadius<0 ) return Flux.empty();

        GeoLocationThreshold originThresholds = new GeoLocationThreshold(originLat, originLong, originRadius);

        return this.findGroupsByOriginWithinThresholds(
                originThresholds.getLatMin(), originThresholds.getLatMax(),
                originThresholds.getLongMin(), originThresholds.getLongMax());
    }

    public Flux<Group> findGroupsByDestinationWithinRadius(
            Double destLat, Double destLong, Double destRadius){

        if (ObjectUtils.isEmpty(destLat) || ObjectUtils.isEmpty(destLong)
                || ObjectUtils.isEmpty(destRadius) || destRadius < 0) return Flux.empty();

        GeoLocationThreshold destThresholds = new GeoLocationThreshold(destLat, destLong, destRadius);

        return this.findGroupsByDestinationWithinThresholds(
                destThresholds.getLatMin(), destThresholds.getLatMax(),
                destThresholds.getLongMin(), destThresholds.getLongMax());
    }

    public Flux<Group> findGroupsByOriginAndDestinationWithinRadius(
            Double originLat, Double originLong, Double originRadius,
            Double destLat, Double destLong, Double destRadius){

        if (ObjectUtils.isEmpty(originLat) || ObjectUtils.isEmpty(originLong)
                || ObjectUtils.isEmpty(destLat) || ObjectUtils.isEmpty(destLong)
                || ObjectUtils.isEmpty(originRadius) || ObjectUtils.isEmpty(destRadius)
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

        if (!ObjectUtils.isEmpty(group)){
            return groupRepository.findById(group.getId())
                    .flatMap(groupInDb -> {
                        log.debug("group in db is: " + groupInDb);
                        log.debug("Update the group");
                        group.setId(groupInDb.getId());
                        log.debug("Group in repository: " + groupInDb);
                        return groupRepository.save(group);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        log.debug("Creating a new User 2");
                        return groupRepository.save(group);
                    }));
            // TODO: ADD ERORR OR SUCCESS HANDLERS*/
        }else{
            // TODO: CREATE ERROR HANDLERS
            log.info("A Null group data is entered. Do not process!");
            return Mono.empty();
        }
    }

    // DELETE
    @Override
    public Mono<Void> deleteById(ObjectId id) {

        if (ObjectUtils.isEmpty(id)) return Mono.empty();

        return groupRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {

        return groupRepository.deleteAll();
    }
}
