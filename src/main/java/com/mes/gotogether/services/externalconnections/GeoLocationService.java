package com.mes.gotogether.services.externalconnections;

import java.util.Optional;

import com.mes.gotogether.domains.Address;

public interface GeoLocationService {
	
	<T> Optional<Double[]> getAddressLongitudeAndLatitude(T searchAddress);	
}
