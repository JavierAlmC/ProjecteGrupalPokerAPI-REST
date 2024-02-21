package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import java.util.List;

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
    private Long whoIsDealer;
    private String tableCards;
    private int blinds = 10;
    private String deck;
    private Long idPlayingNow;
    private int minDealValue = 10;
    @OneToMany(mappedBy = "idState")
    private List<PlayerDb> usuarios;
}
