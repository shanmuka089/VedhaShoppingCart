package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserModel;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Integer> {
	
	Optional<UserModel> findByusername(String name);
	
	Optional<UserModel> findByusermail(String mail);

}
