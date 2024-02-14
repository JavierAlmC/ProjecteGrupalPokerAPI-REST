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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PartidaList;
import com.grup.pokerdaw.api_rest_pokerdaw.security.dto.Mensaje;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.PartidaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PartidaRestController {
    private final PartidaService partidaService;
    private final GameStateService gameStateService;
    /*
     * @Autowired
     * UsuarioRepository usuarioRepository;
     */

    public PartidaRestController(PartidaService partidaService, GameStateService gameStateService) {
        this.partidaService = partidaService;
        this.gameStateService = gameStateService;
    }

    @GetMapping("/partidas")
    public ResponseEntity<Map<String, Object>> getAllPartidas(
            @RequestParam(required = false) String descripcion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "idGame,asc") String[] sort) {
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
            PaginaDto<PartidaList> paginaPartidasList = partidaService.findAll(paging);
            if (descripcion == null)
                paginaPartidasList = partidaService.findAll(paging);
            else
                paginaPartidasList = partidaService.getByDescripcionContaining(descripcion, paging);
            List<PartidaList> partidas = paginaPartidasList.getContent();
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

    @PostMapping("/nuevaPartida")
    public ResponseEntity<?> nuevaPartida(@Valid @RequestBody PartidaDb partidaDb) {
        partidaDb = partidaService.saveAndFlush(partidaDb);
        GameStateDb gameStateDb = new GameStateDb();
        gameStateDb.setPartidaDb(partidaDb);
        gameStateService.save(gameStateDb);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Partida creada"));
    }
    
    @GetMapping("/partida/usrsInGame")
    public ResponseEntity<?> getUsuariosInGame(@RequestParam(required = true) Long idGame) {
        Integer nPlayers = partidaService.getPlayersInGame(idGame);
        Map<String,Integer> body = new HashMap<>();
        body.put("players",nPlayers);
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/partida/{id}")
    public ResponseEntity<?> deleteGameById( @PathVariable("id") Long idGame){
        if (partidaService.deleteGameById(idGame))
            return ResponseEntity.ok().body("Game deleted");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Game not found");
    }
}
