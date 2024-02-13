package com.grup.pokerdaw.api_rest_pokerdaw.srv.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.GameStateRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;

import io.micrometer.common.lang.NonNull;

@Service
public class GameStateServiceImpl implements GameStateService{
    private final GameStateRepository gameStateRepository;

    public GameStateServiceImpl(GameStateRepository gameStateRepository){
        this.gameStateRepository=gameStateRepository;
    }

    @Override
    public void save(@NonNull GameStateDb gameState) {
        gameStateRepository.save(gameState);
    }

    @Override
    public Optional<GameStateDb> findGameStateById(Long id){
        Optional<GameStateDb> gameState = gameStateRepository.findById(id);
        if (gameState.isPresent())
            return gameState;
        else
            return Optional.empty();
    }
}
