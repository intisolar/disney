package com.backend.challenge.disney.repositories;

import com.backend.challenge.disney.entities.CharacterEntity;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    
    @Query("SELECT c.name, c.picture FROM CharacterEntity c") 
    public ArrayList<CharacterEntity> findList();
    
    @Query("SELECT c FROM CharacterEntity c WHERE c.name = :name") 
    public CharacterEntity findByName(@Param("name") String name);
    
}
