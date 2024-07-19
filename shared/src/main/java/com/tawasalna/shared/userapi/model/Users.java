package com.tawasalna.shared.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tawasalna.shared.communityapi.model.Community;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    @Indexed(unique = true)
    @Email
    private String email;

    @Email
    private String secondaryemail;

    private String username;

    private String address;

    @JsonIgnore
    private String password;

    @NotBlank
    @DocumentReference
    private Set<Role> roles;

    private AccountStatus accountStatus;

    private String answer1;
    private String answer2;
    private String answer3;

    @DocumentReference
    @JsonIgnore
    private UserVerifCode userVerifCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BusinessProfile businessProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResidentProfile residentProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProspectProfile prospectProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AgentProfile agentProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BrokerProfile brokerProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AdminProfile adminProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean firstLoggedIn;

    @DocumentReference
    @JsonIgnore
    private Community community;

    public String getName() {
        return this.username;
    }
}
