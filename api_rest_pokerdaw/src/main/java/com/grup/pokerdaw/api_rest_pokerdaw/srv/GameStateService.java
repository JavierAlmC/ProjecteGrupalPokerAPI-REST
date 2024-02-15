package com.grup.pokerdaw.api_rest_pokerdaw.srv;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateList;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;

import io.micrometer.common.lang.NonNull;

public interface GameStateService {
    public void save(@NonNull GameStateDb gameStateDb);

    public boolean deleteById(Long idGame);

    public PaginaDto<GameStateList> findAll(Pageable paging);

    public PaginaDto<GameStateList> getByGameStateNameContaining(String gameStateName, Pageable paging);

    public Integer getPlayersInGame(Long idGame);

    public Optional<GameStateDb> findGameStateById(Long idState);

    public Optional<GameStateDb> findGameStateByIdGame(Long idGame);

    public boolean newRound(Long id);
}
