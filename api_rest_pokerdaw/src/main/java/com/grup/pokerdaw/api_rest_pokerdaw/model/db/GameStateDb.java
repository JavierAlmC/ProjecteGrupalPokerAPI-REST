package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import java.util.List;

import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.UsuarioInGame;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "gameState")
public class GameStateDb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idState;
    private String gameStateName;
    private int deal = 0;
    private String round = "Preflop";
    private int whoIsDealer = 0;
    private String tableCards;
    private int blinds = 10;
    private String deck;
    private int playingNow = 0;
    private int minDealValue = 10;
    @OneToMany(mappedBy = "gameStateDb")
    private List<UsuarioDb> usuarios;
}
