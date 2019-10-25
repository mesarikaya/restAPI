package com.mes.gotogether.domains.responses;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import static java.util.stream.Collectors.*;

import com.mes.gotogether.domains.Group;
import com.mes.gotogether.domains.User;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@ToString
public class GroupSearchResponse {
	
	private final String name;
	private final GroupDetails groupDetails;
	private final Members members;
	
	public GroupSearchResponse(Group group) {
		
		Objects.requireNonNull(group);
		Objects.requireNonNull(group.getOriginAddress());
		Objects.requireNonNull(group.getDestinationAddress());
		this.name = group.getName();
		this.groupDetails = new GroupDetails(group.getOriginAddress().getCity(),
											 group.getOriginAddress().getZipcode(),
											 group.getOriginSearchRadius(),
											 group.getDestinationAddress().getCity(),
											 group.getDestinationAddress().getZipcode(),
											 group.getDestinationSearchRadius()
											);
		
		this.members = new Members(group.getMembers().stream()
				 									 .map(member -> member.getFirstName() + " " + member.getLastName())
				 									 .collect(toCollection(HashSet::new)));
	}

	@Getter
	@ToString
	private static class GroupDetails{
		
		private final String originCity;
		private final String originZipCode;
		private final double originRange;
		private final String destinationCity;
		private final String destinationZipCode;
		private final double destinationRange;
		
		public GroupDetails(String originCity, String originZipCode, 
						    double originRange, String destinationCity,
						    String destinationZipCode, double destinationRange) {
			
			this.originCity = originCity;
			this.originZipCode = originZipCode;
			this.originRange = originRange;
			this.destinationCity = destinationCity;
			this.destinationZipCode = destinationZipCode;
			this.destinationRange = destinationRange;
		}
	}
	
	@Getter
	@ToString
	private static class Members {
		
		private final HashSet<String> userNames;
		
		public Members(HashSet<String> userNames) {
			this.userNames = userNames;
		}
	}
}

