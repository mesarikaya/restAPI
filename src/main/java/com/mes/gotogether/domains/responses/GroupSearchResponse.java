package com.mes.gotogether.domains.responses;

import com.mes.gotogether.domains.Group;
import java.util.HashSet;
import java.util.Objects;
import static java.util.stream.Collectors.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Slf4j
@Data
@Getter
@ToString
public class GroupSearchResponse {
	
	private final ObjectId id;
	private final String name;
	private final GroupDetails groupDetails;
	private final Members members;
	
	public GroupSearchResponse(Group group) {
		
                             log.info("*******CONVERTING TO GROUP RESPONSE******");
                            Objects.requireNonNull(group);
                            Objects.requireNonNull(group.getOriginAddress());
                            Objects.requireNonNull(group.getDestinationAddress());
                            this.id = group.getId();
                            this.name = group.getName();
                            this.groupDetails = new GroupDetails(group.getOriginAddress().getCity(),group.getOriginAddress().getZipcode(),
                                                       group.getOriginSearchRadius(), group.getDestinationAddress().getCity(),
                                                       group.getDestinationAddress().getZipcode(),group.getDestinationSearchRadius());
                                                    this.members = new Members(group.getMembers().stream()
                                                                                                                                          .map(member -> new User(member.getId(),
                                                                                                                                                                                        member.getFirstName() + " " + member.getLastName(), 
                                                                                                                                                                                        group.getOwners().contains(member)))
                                                                                                                                        . collect(toCollection(HashSet::new)));

                            log.info("Created member is: " + this.members);
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
		
		private final HashSet<User> users;
		
		public Members(HashSet<User> users) {
			this.users = users;
		}
	}
	
	@Getter
	@ToString
	private static class User {
		
		private ObjectId id;
		private final String userName;
		private final boolean isOwner;
		
		public User(ObjectId id, String userName, boolean isOwner) {
			this.id = id;
			this.userName = userName;
			this.isOwner = isOwner;
		}
	}
}

