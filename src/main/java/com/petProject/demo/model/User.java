package com.petProject.demo.model;

import java.util.Collection;
import java.util.List;

import com.petProject.demo.common.type.AccountTypes;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Setter
@Getter
@Builder
@Document("USERS")
public class User implements UserDetails {
    private String userId;

    private String email;
    
    private String password;

    private List<Car> cars;

    private String role;

    private AccountTypes type;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

}
