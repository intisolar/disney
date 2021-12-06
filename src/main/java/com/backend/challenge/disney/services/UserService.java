package com.backend.challenge.disney.services;

import com.backend.challenge.disney.entities.UserEntity;
import com.backend.challenge.disney.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void register(String username, String email, String password, String password1) throws ErrorService {
       System.out.println("////////////////////////////////////////////////");
        System.out.println("Entra al método");
        UserEntity ifTheyExist = userRepository.findByEmail(email);
        if (ifTheyExist == null) {
            
System.out.println("!!!!!!!! ENTRA AL IF  EMAIL");
            UserEntity otheruser = userRepository.findByUsername(username);
            if (username != otheruser.getUsername()) {
                
                System.out.println("!!!!!!!! ENTRA AL IF USERNAME");
                validate(username, email, password, password1);

                UserEntity usuario = new UserEntity(); //Creamos un objeto usuario
                usuario.setUsername(username); // lo llenamos con los datos que nos llega del registro web
                usuario.setEmail(email);

                String encriptada = new BCryptPasswordEncoder().encode(password);
                usuario.setPassword(encriptada);
                usuario.setEnableDate(new Date());

                //persisto el usuario
                userRepository.save(usuario);
            } else {
                throw new ErrorService("Ese nombre de usuario ya se encuentra registrado.");
            }

        } else {
            throw new ErrorService("Ya se encuentra un usuario registrado con ese email.");
        }
    }

    //ESTO ES PARA QUE UN USUARIO LOGUEADO SOLO PUEDE MODIFICAR SUS DATOS
    //    @PreAuthorize("hasAnyRole('ROLE_USUARIO_AUTORIZADO')")
    @Transactional
    public void modify(String id, String username, String email, String pass, String pass2) throws ErrorService {

        validate(username, email, pass, pass2);

        Optional<UserEntity> response = userRepository.findById(id); //mediante la clase Optional nos fijamos si con el id devuelve un usuario

        if (response.isPresent()) { //si response devuelve un usuario entonces modificalo
            UserEntity usuario = response.get();
            usuario.setUsername(username); // seteamos el nuevo dato
            usuario.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(pass);
            usuario.setPassword(encriptada);

            userRepository.save(usuario); //Le decimos al repositorio que lo guarde en la base de datos. El repositorio es el encargado de transformar ese objeto en una o más tablas de la base de datos

        } else {
            throw new ErrorService("El usuario solicitado no se encuentra registrado.");
        }
    }

    @Transactional
    public void disable(String id) throws ErrorService {
        Optional<UserEntity> response = userRepository.findById(id); //mediante la clase Optional nos fijamos si con el id devuelve un usuario
        if (response.isPresent()) { //si response devuelve un usuario entonces modificalo
            UserEntity user = response.get();
            user.setDisableDate(new Date());

            userRepository.save(user); //Le decimos al repositorio que lo guarde en la base de datos. El repositorio es el encargado de transformar ese objeto en una o más tablas de la base de datos

        } else {
            throw new ErrorService("El usuario solicitado no se encuentra registrado.");
        }
    }

    @Transactional
    public void enable(String id) throws ErrorService {
        Optional<UserEntity> response = userRepository.findById(id); //mediante la clase Optional nos fijamos si con el id devuelve un usuario
        if (response.isPresent()) { //si response devuelve un usuario entonces modificalo
            UserEntity user = response.get();
            user.setDisableDate(null); //borramos la fecha de baja

            userRepository.save(user); //Le decimos al repositorio que lo guarde en la base de datos. El repositorio es el encargado de transformar ese objeto en una o más tablas de la base de datos

        } else {
            throw new ErrorService("El usuario solicitado no se encuentra registrado.");
        }
    }
//

    private void validate(String username, String email, String pass, String pass2) throws ErrorService {

        if (username == null || username.isEmpty()) {
            throw new ErrorService("Escriba un nombre de usuario válido.");
        }

        if (email == null || email.isEmpty()) {
            throw new ErrorService("El campo 'correo electrónico' no puede estar vacío.");
        }

        if (pass == null || pass.length() < 6) {
            throw new ErrorService("La clave debe tener al menos 6 caracteres.");
        }
        if (!pass.equals(pass2)) {
            throw new ErrorService(("Las claves no coinciden."));
        }

    }
//
    //Este método me permite autenticar el usuario de forma segura//
//
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userquery = userRepository.findByUsername(username); // busco al usuario por el id username

        if (userquery != null) { // si existe
            //Esto es lo que le da los permisos al usuario, a que modulos puede acceder
            List<GrantedAuthority> permisos = new ArrayList();  // le crea permisos

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_AUTHORIZED_USER");
            permisos.add(p1);

//Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); //Esta llamada va a recuperar los atributos del request
            HttpSession session = attr.getRequest().getSession(true); // una vez que trae la solicitud va a solicitar los datos de sesion de esa solicitud de http
            session.setAttribute("usuariosession", userquery); // en los datos de sesion vioy a guardar un atributo que se va a llamar usuario session. Ese atributo va a guardar el objeto usuario que acabo de ir a buscar a la BD

            System.out.println("//////////////////////////////////////////////////");
            System.out.println(userquery.getUsername());

            User secureUser = new User(userquery.getUsername(), userquery.getPassword(), permisos);

            return secureUser;

        } else {
            return null;
        }
    }

    public UserEntity secureFindByID(String id) throws ErrorService {
        Optional<UserEntity> response = userRepository.findById(id);

        if (response.isPresent()) {
            UserEntity user = response.get();
            return user;
        } else {

            throw new ErrorService("El usuario no se encuentra registrado.");
        }
    }

}
