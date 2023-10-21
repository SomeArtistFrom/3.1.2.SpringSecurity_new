package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User showOneUser(int id) {
        Optional<User> findOneUserById = userRepository.findById(id);
        return findOneUserById.orElse(null);
    }

//    @Transactional
//    public boolean save(User user) {
//        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
//
//        if (userFromDB != null) {
//            return false;
//        }
//
//        user.setRoles(Collections.singleton(new Role(2, "ROLE_USER")));
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        userRepository.save(user);
//        return true;
//    }

    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));
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


//    public List<User> showAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User showOneUser(int id) {
//        return userRepository.findById(id).get();
//    }
//
//    @Transactional
//    public void save(User user) {
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        userRepository.save(user);
//    }


    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
////
//    @Transactional
//    public void update(int id, User updatedUser) {
//        updatedUser.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));
//        userRepository.save(updatedUser);
//    }
//
//    @Transactional
//    public void delete(int id) {
//        userRepository.delete(userRepository.findById(id).get());
//    }
}