package com.humber.Week8_Assignment_3.services;

import com.humber.Week8_Assignment_3.models.ItemUser;
import com.humber.Week8_Assignment_3.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemUserService implements UserDetailsService {
    private final UserRepository userRepository;
    public ItemUserService(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //attempts to find the user by their username
        Optional<ItemUser> userOp = userRepository.findByUsername(username);

        // if the user is found, convert MyUser into a Spring security UserDetail object
        if(userOp.isPresent()){
            ItemUser user = userOp.get();

            return User.builder() //Sets the fields of the User from the registry page, username, password, and assigns the Role
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }else{ //if user isn't found
            throw new UsernameNotFoundException("User not found");
        }
    }
}
