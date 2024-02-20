package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "partidas")
public class PartidaDb implements Serializable{
    @Id
    @Column(name = "idGame", nullable = false, unique = true)
    private Long idGame;
    @NotNull
    private String descripcion;
    @NotNull
    private String gameState;
}