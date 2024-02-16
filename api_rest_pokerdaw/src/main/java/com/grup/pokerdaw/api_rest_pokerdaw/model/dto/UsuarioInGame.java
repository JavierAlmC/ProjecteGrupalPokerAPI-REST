package com.grup.pokerdaw.api_rest_pokerdaw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioInGame {
    private Long id;
    private String nickname;
    private Long idState;
}
