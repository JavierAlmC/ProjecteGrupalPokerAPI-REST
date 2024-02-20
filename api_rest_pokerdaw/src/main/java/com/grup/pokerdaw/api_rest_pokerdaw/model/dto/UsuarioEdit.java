package com.grup.pokerdaw.api_rest_pokerdaw.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioEdit {
    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotNull(message = "El email no puede ser nulo")
    private String email;
}
