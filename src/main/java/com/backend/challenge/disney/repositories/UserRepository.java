/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.repositories;

import com.backend.challenge.disney.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    
    @Query("SELECT c FROM UserEntity c WHERE c.email = :email")
    public UserEntity findByEmail(@Param("email") String email);
    
    @Query("SELECT c FROM UserEntity c WHERE c.username = :username")
    public UserEntity findByUsername(@Param("username") String username);
}
