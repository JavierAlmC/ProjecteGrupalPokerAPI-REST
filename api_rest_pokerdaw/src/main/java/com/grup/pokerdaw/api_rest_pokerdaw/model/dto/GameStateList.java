package com.grup.pokerdaw.api_rest_pokerdaw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameStateList {
    private Long idState;
    private String gameStateName;
    private Integer nPlayers;
}
