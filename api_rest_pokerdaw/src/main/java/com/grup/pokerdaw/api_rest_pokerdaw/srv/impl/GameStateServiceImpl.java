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
    @Override
    public Optional<GameStateDb> findGameStateByIdGame(Long id){
        Optional<GameStateDb> gameState = gameStateRepository.findByPartidaDbIdGame(id);
        if (gameState.isPresent())
            return gameState;
        else
            return Optional.empty();
    }
    @Override
    public boolean newRound(Long idGame){
        Optional<GameStateDb> optionalGameState = gameStateRepository.findByPartidaDbIdGame(idGame);
        if (optionalGameState.isPresent()) {
            GameStateDb gameStateDb = optionalGameState.get();
            gameStateDb.setDeal(0);
            gameStateDb.setRound("Preflop");
            //gameStateDb.setWhoIsDealer(0); no se encara com iterar sobre els users
            //gameStateDb.setTableCards(""); aci tocara serialitzar crec
            gameStateDb.setMinDealValue(gameStateDb.getBlinds());
            //gameStateDb.setDeck(); igual que en tableCards
            //gameStateDb.setPlayingNow(0);
            // giveCardsToPlayers()
            //gameStateRepository
            return true;
        } else
            return false;
        
    }
}
