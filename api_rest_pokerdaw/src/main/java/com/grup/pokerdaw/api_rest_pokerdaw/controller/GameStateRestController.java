package com.grup.pokerdaw.api_rest_pokerdaw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;

@RestController
@RequestMapping("/api/v1")
public class GameStateRestController {
    private final GameStateService gameStateService;

    public GameStateRestController(GameStateService gameStateService){
        this.gameStateService=gameStateService;
    }

    /*@GetMapping("/gameState/")
    public ResponseEntity<?> getGameStateById(
        @RequestParam Long id
    ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(gameStateService.findGameStateById(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }*/
}
