package com.grup.pokerdaw.api_rest_pokerdaw.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios_partidas")
public class UsuarioPartidaDb {
    @Id
    private Long idGame;   
    @Id
    private Long idUsuario;
}
