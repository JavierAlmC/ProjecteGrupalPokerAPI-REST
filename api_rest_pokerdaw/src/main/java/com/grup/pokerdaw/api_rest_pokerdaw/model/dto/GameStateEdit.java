package com.grup.pokerdaw.api_rest_pokerdaw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameStateEdit {
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
}
