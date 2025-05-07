package com.humber.Week8_Assignment_3.repositories;

import com.humber.Week8_Assignment_3.models.ItemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ItemUser, Long> {

    //get optional user by their username
    public Optional<ItemUser> findByUsername(String username);
}
