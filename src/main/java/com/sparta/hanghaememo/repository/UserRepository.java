package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
