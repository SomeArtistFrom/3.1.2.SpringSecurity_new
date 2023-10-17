package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "table_users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 30, message = "name should be > 2 and < 30 char")
    @Column(name = "username")
    private String username;

    @NotNull(message = "age should not be empty")
//    @Size(min = 14, max = 125, message = "age should be >= 14 and <=125 years")
    @Min(value = 14, message = "age should be >= 14")
    @Max(value = 125, message = "age should be <= 125")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "password should not be empty")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "profession should not be empty")
    @Column(name = "profession")
    private String profession;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "table_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String username, int age, String profession) {
        this.username = username;
        this.age = age;
        this.profession = profession;
    }

    public User(String username, int age, String password, String profession, Set<Role> roles) {
        this.username = username;
        this.age = age;
        this.password = password;
        this.profession = profession;
        this.roles = roles;
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", profession='" + profession + '\'' +
                ", roles=" + roles +
                '}';
    }
}
