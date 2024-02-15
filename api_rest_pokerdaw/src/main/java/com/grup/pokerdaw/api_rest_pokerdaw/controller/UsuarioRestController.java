package com.grup.pokerdaw.api_rest_pokerdaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.PartidaRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.Mensaje;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.service.UsuarioService;

@RestController
@RequestMapping("/api/v1")
public class UsuarioRestController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PartidaRepository partidaRepository;

    @PutMapping("/joinGame/{id}/{idGame}")
    public ResponseEntity<?> putMethodName(@PathVariable("id") Long id, @PathVariable("idGame") Long idGame) {
        Optional<UsuarioDb> optionalUsuario = usuarioRepository.findById(id);
        Optional<PartidaDb> optionalPartida = partidaRepository.findById(idGame);
        if (optionalUsuario.isPresent() && optionalPartida.isPresent()) {
            UsuarioDb usuario = optionalUsuario.get();
            PartidaDb partida = optionalPartida.get();
            usuario.setPartidaDb(partida);
            partida.getUsuarios().add(usuario);
            usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario.getNickname() + " has joined the game");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: NO SE PUDO UNIR");
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
}
