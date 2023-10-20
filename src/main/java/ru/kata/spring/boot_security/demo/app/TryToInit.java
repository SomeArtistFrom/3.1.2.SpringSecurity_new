package ru.kata.spring.boot_security.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

public class TryToInit {
    @Component
    public class CommandLineRunnerImpl implements CommandLineRunner {
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        @Autowired
        public CommandLineRunnerImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public void run(String... arg) throws Exception {

            Role adminR = new Role(1, "ROLE_ADMIN");
            Role userR = new Role(2, "ROLE_USER");
            Set<Role> adminRoles = new HashSet<>();
            Set<Role> userRoles = new HashSet<>();

            roleRepository.save(adminR);
            roleRepository.save(userR);
            adminRoles.add(adminR);
            adminRoles.add(userR);
            userRoles.add(userR);


            String rawUser = "user";
            String rawAdmin = "admin";
            String encodedUser = passwordEncoder.encode(rawUser);
            String encodeAdmin = passwordEncoder.encode(rawAdmin);

            User user = new User("user", 28, encodedUser, "UserPr", userRoles);
            User admin = new User("admin", 89, encodeAdmin, "AdminPr", adminRoles);

            System.out.println(user);
            userRepository.save(user);
            System.out.println(admin);
            userRepository.save(admin);
        }
    }
}
