package com.grup.pokerdaw.api_rest_pokerdaw.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Mensaje {

    private String string;

    public Mensaje(String string) {
        this.string = string;
    }

}
