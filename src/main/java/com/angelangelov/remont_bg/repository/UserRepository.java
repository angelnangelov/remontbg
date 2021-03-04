package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUsername(String username);
    Optional<User> findUserByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
}
