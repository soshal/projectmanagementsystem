package com.soshal.repository;

import com.soshal.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    User findbyEmail(String email);
}
