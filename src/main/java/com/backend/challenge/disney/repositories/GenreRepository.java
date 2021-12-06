
package com.backend.challenge.disney.repositories;

import com.backend.challenge.disney.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String>{
    
}
