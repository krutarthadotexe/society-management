package com.sm.society_management.repositories;

import com.sm.society_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findByWing(String wing);   // ✅ NEW
}


