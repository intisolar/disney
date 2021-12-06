/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.controllers;

import com.backend.challenge.disney.entities.UserEntity;
import com.backend.challenge.disney.services.ErrorService;
import com.backend.challenge.disney.services.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index.html"; //write the name of the index page
    }

    @GetMapping("/auth/login")
    public String login(ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {
        if (error != null) {
            modelo.put("error", "Usuario o Clave incorrectos");
        }
        //esto es para que tire un mensaje una vez salido de la plataforma, falta agregar en el index o modifcar las opciones dependiendo si el usuario esta logueado poner logout o si no se logueo poner login
        if (logout != null) {
            modelo.put("logout", "Ha salido correctamente");
        }
        return "login.html"; //nombre de la vista para login
    }

    @GetMapping("/auth/register")
    public String register(ModelMap model) {

        return "register.html"; //AGREGAR VISTA DE REGISTRO
    }

    @PostMapping("/register-new-user")
    public String registrar(ModelMap model, @RequestParam String username, @RequestParam String email, @RequestParam String pass1, @RequestParam String pass2) {

        try {
            System.out.println("////////////////////////////////////////////////");
            System.out.println("Hasta esta linea va");
            userService.register(username, email, pass1, pass2);
            //el titulo y la descripcion hay que ponerlo en el index con thymeleaf
            model.put("titulo", "Welcome words"); // CAMBIAR POR LA FRASE DE BIENVENIDA
            model.put("descripcion", "You have successfully registered.");
            return "/index";
        } catch (ErrorService ex) {

            model.put("error", ex.getMessage());
            model.put("username", username);
            model.put("email", email);
            model.put("pass1", pass1);
            model.put("pass2", pass2);

            return "register.html"; //VISTA DE REGISTRO
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_AUTHORIZED_USER')")
    @GetMapping("/modify-profile")
    public String modifyProfile(HttpSession session, @RequestParam String username, ModelMap model) {

        //esto evita que una persona que sabe un id de otra pueda entrar y modificar sus datos, habilitar cuando se habilite el preAuthorize
        UserEntity login = (UserEntity) session.getAttribute("usuariosession");
        if (login == null || !login.getUsername().equals(username)) {
            return "redirect:/"; //configurar con la vista a la que redirigir si no tiene los permisos
        }

        try {
            UserEntity usuario = userService.secureFindByID(username);
            model.addAttribute("perfil", usuario);
        } catch (ErrorService e) {
            model.addAttribute("error", e.getMessage());
        }

        return "update-profile.html"; //Configurar con la vista para editar perfil si se autentica exitosamente
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_AUTORIZADO')")
    @PostMapping("/action-update-profile")
    public String updateProfile(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String username, @RequestParam String email, @RequestParam String pass1, @RequestParam String pass2) {
        UserEntity user = new UserEntity();
        try {

            user = userService.secureFindByID(username);
            userService.modify(id, username, email, pass1, pass2);
            session.setAttribute("usuariosession", user);

            return "redirect:/"; //Escribir nombre de la vista a la que va a ser redirigido si actualizó con éxito "redirect:/inicio" 
        } catch (ErrorService e) {
            modelo.put("error", e.getMessage());
            modelo.put("profile", user);
            return "update-profile.html"; // Escribir el nombre de la vista de editar perfil para que vuelva a intentarlo
        }
    }

    @GetMapping("/characters")
    public String getCharacters(){
        
        
        return "characters.html";
    }
    
    @GetMapping("/movies-and-series")
    public String moviesAndSeries(){
        return"movies-and-series.html";
    }
}
