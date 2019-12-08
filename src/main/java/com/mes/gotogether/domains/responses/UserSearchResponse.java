package com.mes.gotogether.domains.responses;

import com.mes.gotogether.domains.Address;
import com.mes.gotogether.domains.User;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Getter
@ToString
public final class UserSearchResponse {
    
            private final String firstName;
            private final String middleName;
            private final String surname;
            private final String username;
            private final Address address;
            
            public UserSearchResponse(User user){
                            log.info("*******CONVERTING TO USER RESPONSE******");
                            Objects.requireNonNull(user);
                            this.firstName = user.getFirstName();
                            this.middleName = user.getMiddleName();
                            this.surname = user.getLastName();
                            this.username = user.getUserId();
                            this.address = user.getAddress();
                            log.info("Created User is: " + this);
            }
    
    
    
    
}
