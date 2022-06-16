package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.RolesModel;

@Repository
public interface RolesModelRepository extends JpaRepository<RolesModel, Integer>{

}
