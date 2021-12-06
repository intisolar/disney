/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.services;

import com.backend.challenge.disney.entities.Picture;
import com.backend.challenge.disney.repositories.PictureRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureService {
    @Autowired
    private PictureRepository pictureRep;
    
    @Transactional        
    public Picture save(MultipartFile file) throws ErrorService {

        if (file != null) {
            try {
                Picture foto = new Picture();
                foto.setMime(file.getContentType());
                foto.setName(file.getName());
                foto.setContent(file.getBytes());
            System.out.println(foto.toString());
                return pictureRep.save(foto);
                
            } catch (Exception e) {
                System.out.println("////////////////////////");
                System.out.println("////////////////////////");
                System.err.print(e.getMessage());
                System.out.println("////////////////////////");
                System.out.println("////////////////////////");
            }
        }
        return null;

    }
    
    @Transactional
    public Picture update(String pictureID, MultipartFile file) throws ErrorService {
        
            if (file != null) {
            try {
                Picture foto = new Picture();
                
                if (pictureID != null) {
                    
                    Optional<Picture> rta = pictureRep.findById(pictureID);
                    
                    if (rta.isPresent()) {
                        
                        foto = rta.get();
                    }
                    
                }
                
                foto.setMime(file.getContentType());
                foto.setName(file.getName());
                foto.setContent(file.getBytes());

                return pictureRep.save(foto);
            } catch (Exception e) {
                
                System.err.print(e.getMessage());
            }
        }
        
        
        return null;
        
    }
}
