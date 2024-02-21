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
import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PlayerDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateEdit;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateList;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.GameStateRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.PlayerRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.GameStateService;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper.GameStateMapper;

import io.micrometer.common.lang.NonNull;

@Service
public class GameStateServiceImpl implements GameStateService {

    private final GameStateRepository gameStateRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlayerRepository playerRepository;

    public GameStateServiceImpl(GameStateRepository gameStateRepository, UsuarioRepository usuarioRepository,
            PlayerRepository playerRepository) {
        this.gameStateRepository = gameStateRepository;
        this.usuarioRepository = usuarioRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void save(@NonNull GameStateEdit gameStateEdit) {
        GameStateDb gameState = GameStateMapper.INSTANCE.gameStateEditToGameStateDb(gameStateEdit);
        gameStateRepository.save(gameState);
    }

    @Override
    public boolean deleteById(Long idGame) {
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
        System.out.println(optionalGameState);
        if (optionalGameState.isPresent()) {

            GameStateDb gameStateDb = optionalGameState.get();
            for (PlayerDb player : gameStateDb.getUsuarios()) {
                UsuarioDb usuario = usuarioRepository.findById(player.getId()).get();
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
    public PaginaDto<GameStateList> getByGameStateNameContaining(String gameStateName, Pageable paging) {
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
    public Optional<GameStateEdit> findGameStateById(Long id) {
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
    public boolean newRound(Long idGame) {
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
        if (optionalGameState.isPresent()) {
            GameStateDb gameStateDb = optionalGameState.get();
            resetPlayers(gameStateDb.getUsuarios());
            gameStateDb.setDeal(0);
            gameStateDb.setRound("Preflop");
            gameStateDb.setWhoIsDealer(nextDealer(gameStateDb.getUsuarios()));
            gameStateDb.setTableCards("[]");
            gameStateDb.setMinDealValue(gameStateDb.getBlinds());
            List<Card> newDeck = newDeck();
            String strNewDeck;
            try {
                strNewDeck = cardsToString(newDeck);
                gameStateDb.setDeck(strNewDeck);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            gameStateDb.setIdPlayingNow(getStartingPlayerId(gameStateDb.getUsuarios()));
            try {
                giveCardsToPlayers(stringToCards(gameStateDb.getDeck()), gameStateDb.getUsuarios());
            } catch (JsonProcessingException e) {
                e.getMessage();
            }

            gameStateRepository.save(gameStateDb);
            return true;
        } else
            return false;

    }

    @Override
    public void nextRound(Long idGame){
        Optional<GameStateDb> optionalGameState = gameStateRepository.findById(idGame);
        if (optionalGameState.isPresent()) {
            GameStateDb gameStateDb = optionalGameState.get();
            switch (gameStateDb.getRound()) {
                case "Preflop":
                    gameStateDb.setRound("Flop");
                    try {
                        gameStateDb.setTableCards(cardsToString(giveCards(3, stringToCards(gameStateDb.getDeck()))));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    resetPlayersStatePlayersThatAreIn(gameStateDb.getUsuarios());
                    break;
                /*case "Flop":
                    gameStateDb.setRound("Turn");
                    try {
                        gameStateDb.setTableCards(cardsToString(
                            List<Card> currentCards = gameStateDb.getTableCards();
                            currentCards.addAll(giveCards(3, stringToCards(gameStateDb.getDeck())))
                            ));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    resetPlayersStatePlayersThatAreIn(gameStateDb.getUsuarios());
                    break;*/
                default:
                    break;
            }
        }
    }

    // SERIALIZATION FUNCTIONS
    @Override
    public String cardsToString(List<Card> cards) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(cards);

    }

    @Override
    public List<Card> stringToCards(String deckStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(deckStr, new TypeReference<List<Card>>() {
        });
    }

    // DECK RELATED FUNCTIONS
    @Override
    public List<Card> newDeck() {
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
    public List<Card> giveCards(Integer nCards, List<Card> deck) {
        List<Card> givenCards = new ArrayList<>(deck.subList(0, nCards));
        deck.subList(0, nCards).clear(); // Eliminar cartas dadas de la baraja
        return givenCards;
    }

    // PLAYER RELATED FUNCTIONS
    @Override
    public void resetPlayers(List<PlayerDb> usuarios) {

        System.out.println(usuarios.size());
        for (PlayerDb player : usuarios) {
            player.setCurrentDeal(0);
            player.setPlayerState("In");
            playerRepository.save(player);
        }
    }
    @Override
    public void resetPlayersStatePlayersThatAreIn(List<PlayerDb> usuarios){
        for (PlayerDb player : usuarios) {
            if (player.getPlayerState()=="Pass")
                player.setPlayerState("In");
            playerRepository.save(player);
        }
    }
    @Override
    public Long nextDealer(List<PlayerDb> usuarios) {
        int i = 0;
        boolean dealerFound = false;
        while (i < usuarios.size() && !dealerFound) {
            PlayerDb usuario = usuarios.get(i);
            if (usuario.getIsDealer() == "true") {
                dealerFound = true;
                usuario.setIsDealer("false");
                if (i == usuarios.size() - 1)
                    usuario = usuarios.get(0);
                else
                    usuario = usuarios.get(i + 1);
                usuario.setIsDealer("true");
                return usuario.getId();
            }
            i++;
        }
        if (!dealerFound)
            usuarios.get(0).setIsDealer("true");

        return 0L;
    }

    @Override
    public Long getStartingPlayerId(List<PlayerDb> usuarios) {
        Optional<PlayerDb> dealerFound = usuarios.stream().filter(usr -> usr.getIsDealer() == "true").findFirst();
        if (dealerFound.isPresent())
            return dealerFound.get().getId();
        else
            return -1L;
    }

    @Override
    public void giveCardsToPlayers(List<Card> deck, List<PlayerDb> usuarios) {
        for (PlayerDb playerDb : usuarios) {
            try {
                playerDb.setCards(cardsToString(giveCards(2, deck)));
            } catch (Exception e) {
                e.getMessage();
            }

        }
    }

    // BUTTON RELATED FUNCTIONS
    @Override
    public void dealAmmount(int ammount, PlayerDb player) {
        if (ammount >= player.getSaldo()) {
            player.setCurrentDeal(player.getCurrentDeal() + player.getSaldo());
            player.setSaldo(0);
            player.setPlayerState("AllIn");
        } else {
            player.setCurrentDeal(player.getCurrentDeal() + ammount);
            player.setSaldo(player.getSaldo()-ammount);
        }
    }
}
