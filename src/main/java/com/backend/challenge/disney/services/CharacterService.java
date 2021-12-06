package com.backend.challenge.disney.services;

import com.backend.challenge.disney.entities.CharacterEntity;
import com.backend.challenge.disney.entities.Picture;
import com.backend.challenge.disney.repositories.CharacterRepository;
import com.backend.challenge.disney.repositories.PictureRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository chaRep;
    @Autowired
    private PictureService pictureService;

    public List<CharacterEntity> ShowNameNPicture() {

        List<CharacterEntity> listaDePersonajes = chaRep.findList();

        return listaDePersonajes; ///ESTA LISTA DEBE SER MOSTRADA EN LAS VISTAS CONIFGURAR Y TAMBIEN EL CONTROLADOR

    }

    public List<CharacterEntity> findAll() {

        List<CharacterEntity> listaDePersonajes = chaRep.findAll();

        return listaDePersonajes; ///ESTA LISTA DEBE SER MOSTRADA EN LAS VISTAS CONIFGURAR Y TAMBIEN EL CONTROLADOR

    }
    public CharacterEntity findByName( String name ){
        
        CharacterEntity result = chaRep.findByName(name);
        
        return result;
    }

    @Transactional
    public void createNewCharacter(MultipartFile picture, String name, Integer age, float weight, String story) throws ErrorService {

        
        validate(name, age, weight, story);
        CharacterEntity otherCha = chaRep.findByName(name);
        if (name != otherCha.getName()) {

            CharacterEntity newCharacter = new CharacterEntity(); //Creamos un objeto cha
            newCharacter.setName(name); // lo llenamos con los datos que nos llega del registro web
            newCharacter.setAge(age);
            newCharacter.setWeight(weight);

            newCharacter.setStory(story);
            Picture foto = pictureService.save(picture);
            newCharacter.setPicture(foto);
            
            chaRep.save(newCharacter);
        } else {
            throw new ErrorService("Ese nombre de personaje ya se encuentra registrado.");
        }

    }
    
    
    
    @Transactional
    public void update(String id, MultipartFile picture, String name, Integer age, float weight, String story) throws ErrorService {

        validate(name, age, weight, story);
        
        Optional<CharacterEntity> respuesta = chaRep.findById(id); //mediante la clase Optional nos fijamos si con el id devuelve un cha
        
        
        if (respuesta.isPresent()) { //si respuesta devuelve un cha entonces modificalo
            CharacterEntity cha = respuesta.get();
            cha.setName(name); // lo llenamos con los datos que nos llega del registro web
            cha.setAge(age);
            cha.setWeight(weight);

            cha.setStory(story);
             
            
            String idFoto = null;

            if (cha.getPicture()!= null) { // si el cha tiene una foto asignada 

                idFoto = cha.getPicture().getId();   // dale a id foto el identificador para que se lo pase al siguiente método

            }
            Picture foto = pictureService.update(idFoto, picture); //Si yo le mando un id nulo igual me va a traer el objeto foto con un id generado automáticamente y el resto de los atributos
            cha.setPicture(foto); 

            chaRep.save(cha); //Le decimos al repositorio que lo guarde en la base de datos. El repositorio es el encargado de transformar ese objeto en una o más tablas de la base de datos

        } else {
            throw new ErrorService("El usuario solicitado no se encuentra registrado");
        }
    }

    @Transactional
    public void delete (String name) {
        
        List<CharacterEntity> listadePersonajes = chaRep.findAll();
        
        for (CharacterEntity aux : listadePersonajes) {
            String Chaname = aux.getName();
            
            if (Chaname.equals(name)) {
                
                chaRep.delete(aux);
                break;
            }
            
        }
        
    }
    
    private void validate ( String name, Integer age, float weight, String story) throws ErrorService{
        
        if (name == null || name.isEmpty()) {
            throw new ErrorService("El campo 'nombre' no puede estar vacío.");
        }

        if (age == null || age < 0 ) {
            throw new ErrorService("El campo 'edad' no puede estar vacío ni ser negativo.");
        }
        if (weight == 0.0 ) {
            throw new ErrorService("El campo 'peso' no puede estar vacío o ser negativo.");
        }

        if (story == null || story.isEmpty()) {
            throw new ErrorService("La historia debe contener alguna descripción.");

    }
}
}