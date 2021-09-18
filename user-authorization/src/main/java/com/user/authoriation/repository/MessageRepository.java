package com.user.authoriation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.authoriation.entity.Message;


/**
 * @author Ramesh Fadatare
 *
 */
public interface MessageRepository extends JpaRepository<Message, Integer>{

}