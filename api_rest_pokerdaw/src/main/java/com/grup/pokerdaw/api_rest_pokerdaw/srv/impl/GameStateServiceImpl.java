package com.grup.pokerdaw.api_rest_pokerdaw.srv.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateList;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.GameStateRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper.GameStateMapper;

import io.micrometer.common.lang.NonNull;

@Service
public class GameStateServiceImpl implements GameStateService{

    private final GameStateRepository gameStateRepository;
    private final UsuarioRepository usuarioRepository;

    public GameStateServiceImpl(GameStateRepository gameStateRepository, UsuarioRepository usuarioRepository){
        this.gameStateRepository=gameStateRepository;
        this.usuarioRepository=usuarioRepository;
    }

    @Override
    public void save(@NonNull GameStateDb gameState) {
        gameStateRepository.save(gameState);
    }
    @Override
    public boolean deleteById(Long idGame){
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
        if (optionalGameState.isPresent()){
            GameStateDb gameStateDb = optionalGameState.get();
            for (UsuarioDb usuario : gameStateDb.getUsuarios()) {
                usuario.setGameStateDb(null);
                usuario = usuarioRepository.save(usuario);
            }
            gameStateRepository.deleteById(idGame);
            return true;
        } else
            return false;
    }
    @Override
    public PaginaDto<GameStateList> findAll(Pageable paging) {
        Page<GameStateDb> paginaGameStateDb = gameStateRepository.findAll(paging);
        return new PaginaDto<GameStateList>(
            paginaGameStateDb.getNumber(),
            paginaGameStateDb.getSize(),
            paginaGameStateDb.getTotalElements(),
            paginaGameStateDb.getTotalPages(),
            GameStateMapper.INSTANCE.gameStatesDbToGameStatesList(paginaGameStateDb.getContent()),
            paginaGameStateDb.getSort());
    }
    @Override
    public PaginaDto<GameStateList> getByGameStateNameContaining(String gameStateName,Pageable paging) {
        Page<GameStateDb> paginaGameStateDb = gameStateRepository.findByGameStateNameContaining(gameStateName, paging);
        return new PaginaDto<GameStateList>(
            paginaGameStateDb.getNumber(),
            paginaGameStateDb.getSize(),
            paginaGameStateDb.getTotalElements(),
            paginaGameStateDb.getTotalPages(),
            GameStateMapper.INSTANCE.gameStatesDbToGameStatesList(paginaGameStateDb.getContent()),
            paginaGameStateDb.getSort());
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
    public Integer getPlayersInGame(Long idGame) {
        Optional<GameStateDb> gameStateDb = gameStateRepository.findById(idGame);
        if (gameStateDb.isPresent())
            return gameStateDb.get().getUsuarios().size();
        else
            return null;
    }
    @Override
    public boolean newRound(Long idGame){
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
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
