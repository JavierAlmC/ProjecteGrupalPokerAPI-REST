package com.grup.pokerdaw.api_rest_pokerdaw.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grup.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.JwtDto;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.LoginUsuario;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.Mensaje;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.NuevoUsuario;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.RolDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.enums.RolNombre;
import com.grup.pokerdaw.api_rest_pokerdaw.security.jwt.JwtService;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.PartidaRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.service.RolService;
import com.grup.pokerdaw.api_rest_pokerdaw.security.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
   

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtService jwtProvider;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PartidaRepository partidaRepository;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Mensaje("Datos incorrectos o email inválido"));
        if (usuarioService.existsByNickname(nuevoUsuario.getNickname()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El nickname del usuario ya existe"));
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El email del usuario ya existe"));

        UsuarioDb usuarioDb = new UsuarioDb(nuevoUsuario.getNombre(), nuevoUsuario.getNickname(),
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()));

        Set<RolDb> rolesDb = new HashSet<>();

        if (nuevoUsuario.getRoles().contains("admin")) {
            rolesDb.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        } else {
            rolesDb.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        }
        usuarioDb.setRoles(rolesDb);
        usuarioService.save(usuarioDb);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario creado"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Datos incorrectos"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getNickname(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return ResponseEntity.status(HttpStatus.OK).body(jwtDto);

    }

    @GetMapping("/infoPerfil/{nickname}")
    public ResponseEntity<?> obtenerDetallesUsuario(@PathVariable String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {

            Optional<UsuarioDb> usuarioDb = usuarioRepository.findByNickname(nickname);

            if (usuarioDb != null) {

                return ResponseEntity.status(HttpStatus.OK).body(usuarioDb);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Usuario no encontrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario no autenticado"));
        }
    }

    @DeleteMapping("/eliminarUsuario/{nickname}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            Optional<UsuarioDb> usuarioDb = usuarioRepository.findByNickname(nickname);

            if (usuarioDb.isPresent()) {
                usuarioRepository.delete(usuarioDb.get());
                return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Usuario eliminado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Usuario no encontrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario no autenticado"));
        }
    }

    @PostMapping("/nuevaPartida/{nickname}")
    public ResponseEntity<?> nuevaPartida(@PathVariable String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PartidaDb partidaDb = new PartidaDb();
        // partidaDb.setState().;


        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {

            Optional<UsuarioDb> usuarioDb = usuarioRepository.findByNickname(nickname);

            if (usuarioDb != null) {
                partidaRepository.save(partidaDb);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Partida creada"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Usuario no encontrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario no autenticado"));
        }
    }

    @GetMapping("/infoPartidas/{nickname}")
    public ResponseEntity<?> obtenerDetallesPartida(@PathVariable String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {

            Optional<UsuarioDb> usuarioDb = usuarioRepository.findByNickname(nickname);
            List<PartidaDb> partidaDb = partidaRepository.findAll();

            if (usuarioDb != null) {
                partidaRepository.findAll();
                return ResponseEntity.status(HttpStatus.OK).body(partidaDb);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Partida no encontrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario no autenticado"));
        }
    }

}
