package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> showAllUsers() ;
    public User showOneUser(int id) ;
    public void save(User user) ;
    public void update(int id, User updatedUser) ;
    public boolean delete(int id) ;

}
