package com.tawasalna.shared.communityapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tawasalna.shared.userapi.model.Users;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;


@Getter
@Setter
@Document(collection = "community")
@AllArgsConstructor
@NoArgsConstructor
public class Community {

    @Id
    private String id;

    @Size(max = 50)
    private String name;

    @Size(max = 100)
    private String description;

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    @DocumentReference
    private Set<Users> admins;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Community{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", admins=" + admins.stream().map(Users::getId).toList() +
                '}';
    }
}
