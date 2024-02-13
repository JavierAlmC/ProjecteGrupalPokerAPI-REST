package com.grup.pokerdaw.api_rest_pokerdaw.srv;

import java.util.Optional;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;

import io.micrometer.common.lang.NonNull;

public interface GameStateService {
    public void save(@NonNull GameStateDb gameStateDb);

    public Optional<GameStateDb> findGameStateById(Long id);
}
