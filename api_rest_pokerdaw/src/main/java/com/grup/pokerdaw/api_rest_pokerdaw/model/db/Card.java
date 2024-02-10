package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    private String suite;
    private Integer value;

}
