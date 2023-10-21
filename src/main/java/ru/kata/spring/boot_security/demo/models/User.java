package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotEmpty(message = "name should not be empty")
//    @Size(min = 2, max = 30, message = "name should be > 2 and < 30 char")
    @Column(name = "username")
    private String username;

//    @NotNull(message = "age should not be empty")
//    @Min(value = 14, message = "age should be >= 14")
//    @Max(value = 125, message = "age should be <= 125")
    @Column(name = "age")
    private int age;

//    @NotEmpty(message = "password should not be empty")
    @Column(name = "password")
    private String password;

//    @NotEmpty(message = "profession should not be empty")
    @Column(name = "profession")
    private String profession;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    }

    public User(String user, int i, String encodedUser, String userPr, Set<Role> userRoles) {
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

}
