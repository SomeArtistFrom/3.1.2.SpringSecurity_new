package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;
    //  private final RoleRepository roleRepository;

    //  private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // User userSuper = new User(user);

//        @Autowired
//        public UserService(RegistrationService registrationService, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//            this.registrationService = registrationService;
//            this.userRepository = userRepository;
//            this.roleRepository = roleRepository;
//            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        }


    public UserServiceImpl(RegistrationService registrationService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    public User showOneUser(int id) {
        Optional<User> findOneUserById = userRepository.findById(id);
        return findOneUserById.orElse(null);
    }

//        @Transactional
//        public boolean save(User user) {
//            Optional<User> userFromDB = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
//
//            if (userFromDB.isPresent()) {
//                return false;
//            }
////            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
////            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
////            userRepository.save(user);
//            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
//            registrationService.register(user);
//            return true;
//        }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
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

//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            User user = userRepository.findByUsername(username);
//
//            if (user==null)
//                throw new UsernameNotFoundException("User not found(");
//
//            return new User(userSuper.getUsername(), userSuper.getAge(), userSuper.getProfession());
//        }
}