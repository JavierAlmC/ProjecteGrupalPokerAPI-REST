package com.grup.pokerdaw.api_rest_pokerdaw.entity;



import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@IdClass(UsuarioDb.class)
@Table(name = "partidas")
public class PartidaDb {
   
    @Id
    private Long idUsuario;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long idGame;
    
    @NotNull
    private GameState state = new GameState();

  
    public static class GameState {
        private int deal = 0; 
        private UsuarioDb[] players = {
            new UsuarioDb(),
            new UsuarioDb(),
            new UsuarioDb(),
            new UsuarioDb(),
        };
        private String round = "Preflop"; 
        private int whoIsDealer = 0; 
        private int[] table = {}; 
        private int blinds = 10;
        private Deck deck = new Deck().newDeck(); 
        private int playingNow = 2; 
        private int minDealValue = 10; 
    }

    public static class Deck {
        public Deck newDeck() {
            return new Deck();
        }
    }
}