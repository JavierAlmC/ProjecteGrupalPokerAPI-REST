package com.grup.pokerdaw.api_rest_pokerdaw.model.db;


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
@Table(name = "usuarios")
public class PlayerDb {
    @Id
    private Long id;
    private String nickname;
    private Integer saldo;
    private String cards;
    private String isDealer;
    private Integer currentDeal;
    private String playerState;
    private Long idState;
}
