package com.grup.pokerdaw.api_rest_pokerdaw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PartidaList;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.PartidaService;

@RestController
@RequestMapping("/api/v1")
public class PartidaRestController {
    private final PartidaService partidaService;
    @Autowired
    UsuarioRepository usuarioRepository;

    public PartidaRestController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping("/partidas")
    public ResponseEntity<Map<String, Object>> getAllPartidas(
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
            List<PartidaList> partidas = paginaPartidasList.getContent();
            Map<String,Object> response = new HashMap<>();
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

}
