package com.grup.pokerdaw.api_rest_pokerdaw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateEdit;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateList;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.Mensaje;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1")
public class GameStateRestController {
    private final GameStateService gameStateService;

    public GameStateRestController(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    // GET REQUESTS
    @GetMapping("/games")
    public ResponseEntity<Map<String, Object>> getAllGames(
            @RequestParam(required = false) String gameStateName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "idState,asc") String[] sort) {
        try {
            List<Order> criteriosOrdenacion = new ArrayList<Order>();
            if (sort[0].contains(",")) {
                for (String criterioOrdenacion : sort) {
                    String[] orden = criterioOrdenacion.split(",");
                    if (orden.length > 1)
                        criteriosOrdenacion.add(new Order(Direction.fromString(orden[1]), orden[0]));
                    else
                        criteriosOrdenacion.add(new Order(Direction.fromString("asc"), orden[0]));
                }
            } else {
                criteriosOrdenacion.add(new Order(Direction.fromString(sort[1]), sort[0]));
            }
            Sort sorts = Sort.by(criteriosOrdenacion);

            Pageable paging = PageRequest.of(page, size, sorts);
            PaginaDto<GameStateList> paginaPartidasList;
            if (gameStateName == null)
                paginaPartidasList = gameStateService.findAll(paging);
            else
                paginaPartidasList = gameStateService.getByGameStateNameContaining(gameStateName, paging);
            List<GameStateList> partidas = paginaPartidasList.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("data", partidas);
            response.put("currentPage", paginaPartidasList.getNumber());
            response.put("pageSize", paginaPartidasList.getSize());
            response.put("totalItems", paginaPartidasList.getTotalElements());
            response.put("totalPages", paginaPartidasList.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/game")
    public ResponseEntity<?> getGameStateById(
            @RequestParam Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gameStateService.findGameStateById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/game/{idGame}/usrsInGame")
    public ResponseEntity<?> getUsuariosInGame(@PathVariable("idGame") Long idGame) {
        Integer nPlayers = gameStateService.getPlayersInGame(idGame);
        Map<String,Integer> body = new HashMap<>();
        body.put("players",nPlayers);
        return ResponseEntity.ok().body(body);
    }

    // POST REQUESTS
    /* 
    @PostMapping("/game")
    public ResponseEntity<?> createGameState() {
        GameStateEdit newGame = new GameStateEdit();
        gameStateService.save(newGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("New GameState created"));
    }*/

    // PUT REQUESTS
    @PutMapping("game/{id}")
    public ResponseEntity<?> newRound(@PathVariable("id") Long id) {
        
        if (gameStateService.newRound(id)) {
            return ResponseEntity.ok().body(new Mensaje("New Round"));
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("ERROR: User not found"));
        }
    }

    // DELETE REQUESTS
    @DeleteMapping("/game/{id}")
    public ResponseEntity<?> deleteGameById( @PathVariable("id") Long idGame){
        if (gameStateService.deleteById(idGame))
            return ResponseEntity.ok().body("Game deleted");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Game not found");
    }
    /*
     * @PutMapping("/deal/{idGame}")
     * public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody
     * String entity) {
     * return entity;
     * }
     */

}
