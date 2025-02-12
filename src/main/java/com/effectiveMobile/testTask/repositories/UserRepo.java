package com.effectiveMobile.testTask.repositories;


import com.effectiveMobile.testTask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String email);
    Optional<User> findByUserEmail(String email);
}
