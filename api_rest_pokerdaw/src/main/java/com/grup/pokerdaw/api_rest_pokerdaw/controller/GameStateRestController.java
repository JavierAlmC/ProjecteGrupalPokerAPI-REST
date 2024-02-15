package com.grup.pokerdaw.api_rest_pokerdaw.controller;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;



@RestController
@RequestMapping("/api/v1")
public class GameStateRestController {
    private final GameStateService gameStateService;

    public GameStateRestController(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @GetMapping("/gameState")
    public ResponseEntity<?> getGameStateById(
            @RequestParam Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gameStateService.findGameStateById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/gameStateByIdGame/{id}")
    public ResponseEntity<?> getGameStateByIdGame(
            @PathVariable("id") Long idPartida) throws RuntimeException {
        Optional<GameStateDb> gameState = gameStateService.findGameStateByIdGame(idPartida);
        if (gameState.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(gameState.get());
        else
            throw new RuntimeErrorException(null, "ERROR: PARTIDA NOT FOUND");
    }

    /*@PutMapping("/deal/{idGame}")
    public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }*/

}
