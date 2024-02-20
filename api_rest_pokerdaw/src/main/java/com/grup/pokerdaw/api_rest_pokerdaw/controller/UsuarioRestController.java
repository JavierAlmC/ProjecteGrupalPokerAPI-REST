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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.UsuarioEdit;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.GameStateRepository;
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
    GameStateRepository gameStateRepository;

    // GET REQUESTS
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

    // PUT REQUESTS
    @PutMapping("/joinGame/{id}/{idGame}")
    public ResponseEntity<?> joinGame(@PathVariable("id") Long id, @PathVariable("idGame") Long idGame) {
        Optional<UsuarioDb> optionalUsuario = usuarioRepository.findById(id);
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
        if (optionalUsuario.isPresent() && optionalGameState.isPresent()) {
            UsuarioDb usuario = optionalUsuario.get();
            GameStateDb gameState = optionalGameState.get();
            usuario.setGameStateDb(gameState);
            gameState.getUsuarios().add(usuario);
            usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario.getNickname() + " has joined the game");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: NO SE PUDO UNIR");
    }

    @PostMapping("/newGame/{idUser}")
    public ResponseEntity<?> createGameStateByUserId(@PathVariable("idUser") Long idUser) {
        Optional<UsuarioDb> optionalUsuario = usuarioRepository.findById(idUser);
        if (optionalUsuario.isPresent()) {
            UsuarioDb usuarioDb = optionalUsuario.get();
            if (usuarioDb.getIdCreatedGame()==null) {
                GameStateDb newGameStateDb = new GameStateDb();
                newGameStateDb = gameStateRepository.save(newGameStateDb);
                usuarioDb.setIdCreatedGame(newGameStateDb.getIdState());
                usuarioService.save(usuarioDb);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("New GameState created"));
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Mensaje("ERROR: User already created a game"));
            }
            

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("ERROR: User not found"));
        }
        
    }


    @PostMapping("/modificarUsuario/{nickname}")
    public ResponseEntity<?> modificarUsuario(@PathVariable String nickname, @RequestBody UsuarioEdit usuarioModificado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            Optional<UsuarioDb> usuarioDb = usuarioRepository.findByNickname(nickname);

            if (usuarioDb.isPresent()) {
                UsuarioDb usuarioExistente = usuarioDb.get();

                usuarioExistente.setNombre(usuarioModificado.getNombre());
                usuarioExistente.setEmail(usuarioModificado.getEmail());

                usuarioRepository.save(usuarioExistente);

                return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Usuario modificado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Usuario no encontrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario no autenticado"));
        }
    }

    // DELETE REQUESTS
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
