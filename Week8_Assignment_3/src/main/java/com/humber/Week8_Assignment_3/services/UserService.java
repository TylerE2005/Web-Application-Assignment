package com.humber.Week8_Assignment_3.services;

import com.humber.Week8_Assignment_3.models.ItemUser;
import com.humber.Week8_Assignment_3.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository; //Allows access to user data in the DB
        this.passwordEncoder = new BCryptPasswordEncoder(); //To encrypt the user passwords
    }

    //save the user to the DB using the registration method
    public int saveUser(ItemUser user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return 0; //indicate a failure because of existing user
        }
        //encrypts the inputted password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //saves the user into the database
        userRepository.save(user);
        return 1;
    }
}
