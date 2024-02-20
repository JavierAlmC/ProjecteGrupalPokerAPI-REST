package com.grup.pokerdaw.api_rest_pokerdaw.srv.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.Card;
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateEdit;
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
    public void save(@NonNull GameStateEdit gameStateEdit) {
        GameStateDb gameState = GameStateMapper.INSTANCE.gameStateEditToGameStateDb(gameStateEdit);
        gameStateRepository.save(gameState);
    }
    @Override
    public boolean deleteById(Long idGame){
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
        System.out.println(optionalGameState);
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
    public Optional<GameStateEdit> findGameStateById(Long id){
        Optional<GameStateDb> gameState = gameStateRepository.findById(id);
        if (gameState.isPresent())
            return Optional.of(GameStateMapper.INSTANCE.gameStateDbToGameStateEdit(gameState.get()));
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
            //resetPlayersState(state.players) deal + state resetejat
            gameStateDb.setDeal(0);
            gameStateDb.setRound("klk");
            //gameStateDb.setWhoIsDealer(0);
            //gameStateDb.setTableCards(""); aci tocara serialitzar crec
            gameStateDb.setMinDealValue(gameStateDb.getBlinds());
            List<Card> newDeck = newDeck();
            String strNewDeck;
            try {
                strNewDeck = cardsToString(newDeck);
                gameStateDb.setDeck(strNewDeck);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //resetPlayersDeal(state.players)
            //gameStateDb.setPlayingNow(0);
            // giveCardsToPlayers()
            gameStateRepository.save(gameStateDb);
            return true;
        } else
            return false;
        
    }

    // SERIALIZATION FUNCTIONS
    @Override
    public String cardsToString(List<Card> cards) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(cards);
            
        
    }
    @Override
    public List<Card> stringToCards(String deckStr) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(deckStr,new TypeReference<List<Card>>(){});
    }

    // DECK RELATED FUNCTIONS
    @Override
    public List<Card> newDeck(){
        List<String> suites = List.of("hearts", "diamonds", "clubs", "spades");
        List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        List<Card> newCards = new ArrayList<>();
        for (String suit : suites) {
            for (Integer value : values) {
                newCards.add(new Card(suit, value));
            }
        } 
        Collections.shuffle(newCards);
        return newCards;
    }
    @Override
    public List<Card> giveCards(Integer nCards, List<Card> deck){
        List<Card> givenCards = List.of();
        givenCards = new ArrayList<>(deck.subList(0, nCards));
        deck.subList(0, nCards).clear(); // Eliminar cartas dadas de la baraja
        return givenCards;
    }

    // PLAYER RELATED FUNCTIONS
    /*@Override
    public void resetPlayers(List<UsuarioDb> usuarios){
        for(UsuarioDb usuario : usuarios){
            usuario.set
        }
    }*/
}
