package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.UserEntity;
 
@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {
	  List<UserEntity> findByfirstNameAndPassword(String firstName, String password);

 
}
