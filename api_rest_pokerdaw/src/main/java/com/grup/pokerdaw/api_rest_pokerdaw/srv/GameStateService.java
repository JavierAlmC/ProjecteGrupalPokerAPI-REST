package com.grup.pokerdaw.api_rest_pokerdaw.srv;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.Card;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateEdit;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateList;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;

import io.micrometer.common.lang.NonNull;

public interface GameStateService {
    public void save(@NonNull GameStateEdit gameStateEdit);

    public boolean deleteById(Long idGame);

    public PaginaDto<GameStateList> findAll(Pageable paging);

    public PaginaDto<GameStateList> getByGameStateNameContaining(String gameStateName, Pageable paging);

    public Integer getPlayersInGame(Long idGame);

    public Optional<GameStateEdit> findGameStateById(Long idState);

    public boolean newRound(Long id);

    // SERIALIZATION FUNCTIONS
    public String cardsToString(List<Card> cards) throws JsonProcessingException;
    public List<Card> stringToCards(String deckStr) throws JsonProcessingException;

    // DECK RELATED FUNCTIONS
    public List<Card> newDeck();
    public List<Card> giveCards(Integer nCards, List<Card> deck);

    // PLAYER RELATED FUNCTIONS
    //public void resetPlayers(List<UsuarioDb> usuarios);
}
