package com.mes.gotogether.services.externalconnections;

import java.util.Optional;

import com.mes.gotogether.domains.Address;

public interface GeoLocationService {
	
	Optional<Double[]> getAddressLongitudeAndLatitude(Address address);	
	Optional<Double[]> getFreeTextLongitudeAndLatitude(String searchAddress);	
}
