package com.user.authoriation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.authoriation.entity.User;

/**
 * @author Ramesh Fadatare
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByEmail(String email);
}