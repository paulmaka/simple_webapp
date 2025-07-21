package com.simplespring.webapp.service;

import com.simplespring.webapp.repository.User;
import com.simplespring.webapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User with such id does not exist.");
        }

        return optionalUser.get();
    }

    public User create(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("User with such email already exists.");
        }

        user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());

        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalStateException("User with such id does not exist.");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String email, String name) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User with such id does not exist.");
        }

        User user = optionalUser.get();

        if (email != null && !email.equals(user.getEmail())) {
            Optional<User> foundByEmail = userRepository.findByEmail(email);
            if (foundByEmail.isPresent()) {
                throw new IllegalStateException("User with such email already exists.");
            }

            user.setEmail(email);
        }

        if (name != null && !name.equals(user.getName())) {
            user.setName(name);
        }

    }
}
