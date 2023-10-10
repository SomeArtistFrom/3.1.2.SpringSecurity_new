package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

    @Service
    @Transactional(readOnly = true)
    public class UserService implements UserDetailsService {

        private final RegistrationService registrationService;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;

        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        User userSuper = new User();

        @Autowired
        public UserService(RegistrationService registrationService, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.registrationService = registrationService;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        public List<User> showAllUsers() {
            return userRepository.findAll();
        }

        public User showOneUser(int id) {
            Optional<User> findOneUserById = userRepository.findById(id);
            return findOneUserById.orElse(null);
        }

        @Transactional
        public boolean save(User user) {
            Optional<User> userFromDB = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));

            if (userFromDB.isPresent()) {
                return false;
            }

            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }

        @Transactional
        public void update(int id, User updatedUser) {
            updatedUser.setId(id);
            userRepository.save(updatedUser);
        }

        @Transactional
        public boolean delete(int id) {
            if (userRepository.findById(id).isPresent()) {
                userRepository.deleteById(id);
                return true;
            }
            return false;
        }

        @Transactional
        public User findUserByUsername(String username) {
            return userRepository.findByUsername(username);
        }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);

            if (user==null)
                throw new UsernameNotFoundException("User not found(");

            return new User(userSuper.getUsername(), userSuper.getAge(), userSuper.getProfession());
        }
    }