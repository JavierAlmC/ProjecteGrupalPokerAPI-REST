package com.grup.pokerdaw.api_rest_pokerdaw.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUsuario {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
}
