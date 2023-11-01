package ru.kata.spring.boot_security.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void addData() throws Exception {

        Role adminR = new Role(1, "ROLE_ADMIN");
        Role userR = new Role(2, "ROLE_USER");

        Set<Role> adminRoles = new HashSet<>();
        Set<Role> userRoles = new HashSet<>();

        roleRepository.save(adminR);
        roleRepository.save(userR);

        adminRoles.add(adminR);
        adminRoles.add(userR);

        userRoles.add(userR);

        String rawUser = "u";
        String rawAdmin = "a";
        String encodedUser = passwordEncoder.encode(rawUser);
        String encodedAdmin = passwordEncoder.encode(rawAdmin);

        User user = new User("user", 28, encodedUser, "UserProf", userRoles);
        User admin = new User("admin", 89, encodedAdmin, "AdminProf", adminRoles);
        User irina = new User("irina", 29, encodedUser, "Artist", userRoles);
        User test = new User("test", 100, encodedUser, "Test", userRoles);

        userRepository.save(user);
        userRepository.save(admin);
        userRepository.save(irina);
        userRepository.save(test);
    }
}