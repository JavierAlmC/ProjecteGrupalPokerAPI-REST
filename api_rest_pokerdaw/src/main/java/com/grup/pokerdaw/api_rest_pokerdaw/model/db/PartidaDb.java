package com.grup.pokerdaw.api_rest_pokerdaw.model.db;

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
public class PartidaDb {
    @Id
<<<<<<< HEAD:api_rest_pokerdaw/src/main/java/com/grup/model/db/PartidaDb.java
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGame;

    /*@Column(columnDefinition = "json")*/
    @NotNull
    private String gameState;
    /*private GameState state = new GameState();
      
      public static class GameState {
      private int deal = 0;
      private UsuarioDb[] players = {};
      private String round = "Preflop";
      private int whoIsDealer = 0;
      private int[] table = {};
      private int blinds = 10;
      private Deck deck = new Deck().newDeck();
      private int playingNow = 0;
      private int minDealValue = 10;
      }
      
      public static class Deck {
      public Deck newDeck() {
      return new Deck();
      }
      }*/
     
=======
    // @Column(name = "idGame", nullable = false, unique = true)
    private Long idGame;
    @NotNull
    private String descripcion;
    @NotNull
    private String gameState;
>>>>>>> Javi:api_rest_pokerdaw/src/main/java/com/grup/pokerdaw/api_rest_pokerdaw/model/db/PartidaDb.java
}