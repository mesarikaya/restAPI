package com.mes.gotogether.domains;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "user_idx", def = "{'userId' : 1}", unique = true, dropDups = true)
})
@Document(collection = "Users")
public class User {

    @Id
    private ObjectId id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String oauthId;
    @Setter(AccessLevel.NONE)
    private String userId;
    private LoginType loginType;
    private String password;
    private Address address;
    private String permalink;
    private String mobileNumber;
    private String lastLogin;
    private boolean isVerified;
    private String verificationToken;
    private Date verificationExpiresAt;
    private boolean isActive;
    @DBRef
    private HashSet<Role> roles;
    @DBRef
    private HashSet<Group> groups;

    public User(ObjectId id, String email, String oauthId) {
        this.id = id;
        this.email = email;
        this.oauthId = oauthId;
        this.userId = email + oauthId;
    }

    public void setEmail(String email) {
        this.email = email;
        this.setUserId();
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
        this.setUserId();
    }

    public void setUserId() {
        this.userId = this.email + this.oauthId;
    }
}
