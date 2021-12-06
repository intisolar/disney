
package com.backend.challenge.disney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.challenge.disney.entities.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String>{
    
}
