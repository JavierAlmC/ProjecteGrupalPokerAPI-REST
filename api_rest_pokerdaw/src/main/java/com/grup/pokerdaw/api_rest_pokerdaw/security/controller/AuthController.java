package com.grup.pokerdaw.api_rest_pokerdaw.security.controller;

import java.util.HashSet;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.JwtDto;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.LoginUsuario;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.Mensaje;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.NuevoUsuario;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.RolDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.enums.RolNombre;
import com.grup.pokerdaw.api_rest_pokerdaw.security.jwt.JwtService;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.service.RolService;
import com.grup.pokerdaw.api_rest_pokerdaw.security.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
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

}
