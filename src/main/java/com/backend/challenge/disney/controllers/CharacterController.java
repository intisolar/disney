/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.controllers;

import com.backend.challenge.disney.entities.CharacterEntity;
import com.backend.challenge.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PreAuthorize("hasAnyRole('ROLE_MANAGER_USER')")
    @GetMapping("/create")
    public String createCharacter() {
        return "create.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER_USER')") //DEFINIR ESTE PERMISO PARA UNA CUENTA
    @PostMapping("/create-new")
    public String newCharacter(ModelMap model, @RequestParam MultipartFile picture, @RequestParam String name, @RequestParam Integer age, @RequestParam float weight, @RequestParam String story) {
        try {
            characterService.createNewCharacter(picture, name, age, weight, story);
            model.put("message", "El personaje se ha creado con éxito");
            return "character.html";

        } catch (Exception e) {

            model.addAttribute("name", name);
            model.addAttribute("age", age);
            model.addAttribute("weight", weight);
            model.addAttribute("story", story);
            model.addAttribute("error", e.getMessage());
            return "create.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER_USER')") //DEFINIR ESTE PERMISO PARA UNA CUENTA
    @GetMapping("/modify-view")
    public String modifyview() {
        return "modify-cha.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER_USER')") //DEFINIR ESTE PERMISO PARA UNA CUENTA
    @PostMapping("/modify")
    public String modifyCharacter(ModelMap model, @RequestParam String id, @RequestParam MultipartFile picture, @RequestParam String name, @RequestParam Integer age, @RequestParam float weight, @RequestParam String story) {
        try {
            characterService.update(id, picture, name, age, weight, story);
            model.put("message", "El personaje se ha modificado con éxito");
            return "character.html";

        } catch (Exception e) {

            model.addAttribute("name", name);
            model.addAttribute("age", age);
            model.addAttribute("weight", weight);
            model.addAttribute("story", story);
            model.addAttribute("error", e.getMessage());
            return "modify-cha.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER_USER')") //DEFINIR ESTE PERMISO PARA UNA CUENTA
    @GetMapping("/delete-view")
    public String delete() {
        return "delete-cha.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER_USER')") //DEFINIR ESTE PERMISO PARA UNA CUENTA
    @PostMapping("/delete")
    public String deleteCha(ModelMap model, String name) {

        try {
            characterService.delete(name);
            model.put("message", "El personaje se ha modificado con éxito");
            return "character.html";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "delete-cha.html";
        }
    }

    @GetMapping("/find")
    public String findByName(ModelMap model, String name){
        
        try {
            CharacterEntity result = characterService.findByName(name);
            model.put("result", result);

            
        } catch (Exception e) {
            model.addAttribute("error", "no se encontró un personaje con ese nombre");
            return "character.html";
        }
        
        return "results.html";
    }
}
