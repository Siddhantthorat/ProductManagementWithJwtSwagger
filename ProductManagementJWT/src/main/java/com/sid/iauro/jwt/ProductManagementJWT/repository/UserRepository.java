package com.sid.iauro.jwt.ProductManagementJWT.repository;



import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sid.iauro.jwt.ProductManagementJWT.models.User;


@Repository
public interface UserRepository extends MongoRepository<User, Integer>{

	Optional<User> findByUsername(String username);



}