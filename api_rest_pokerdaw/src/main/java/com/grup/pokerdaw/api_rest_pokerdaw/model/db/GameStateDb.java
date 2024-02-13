package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    private Long idState;
    private int deal = 0;
    //private UsuarioDb[] players = {};
    private String round = "Preflop";
    private int whoIsDealer = 0;
    //private Card[] table = {};
    private int blinds = 10;
    //private Deck deck = new Deck();
    private int playingNow = 0;
    private int minDealValue = 10;
    @OneToOne
    @JoinColumn(name = "idGame", referencedColumnName = "idGame")
    private PartidaDb partidaDb;
}
