package com.mes.gotogether.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "user_idx", def = "{'userId' : 1}", unique = true, dropDups = true)
})
@Document(collection = "Users")
public class User {

    // public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(8);

    @Id
    private ObjectId id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String oauthId;
    @JsonIgnore
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
    private List<Role> roles;
    @DBRef
    private HashSet<Group> groups;

    public User(User user){
        this(
                user.getId(),
                user.getEmail(),
                user.getOauthId(),
                user.getPassword(),
                user.getRoles()
        );
    }

    @PersistenceConstructor
    public User(ObjectId id, String email, String oauthId, String password, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.oauthId = oauthId;
        this.userId = email + oauthId;
        this.password = password;
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
        this.setUserId();
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
        this.setUserId();
    }

    private void setUserId() {
        this.userId = this.email + this.oauthId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
