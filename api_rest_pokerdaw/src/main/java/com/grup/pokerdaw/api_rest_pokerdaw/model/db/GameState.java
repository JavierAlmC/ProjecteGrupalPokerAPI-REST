package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameState {
    private int deal = 0;
    //private UsuarioDb[] players = {};
    private String round = "Preflop";
    private int whoIsDealer = 0;
    private int[] table = {};
    private int blinds = 10;
    private Deck deck = new Deck();
    private int playingNow = 0;
    private int minDealValue = 10;
}
