package com.grup.pokerdaw.api_rest_pokerdaw.security.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @Column(unique = true)
    private String nickname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Integer saldo = 1000;
    @NotNull
    private Integer totalApostado = 0;
    private String cards; // Mapeja a List<Cards>
    private String isDealer;
    private Integer currentDeal;
    private String playerState;
    private Long idCreatedGame;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idRol"))
    private Set<RolDb> roles = new HashSet<>();
    @ManyToOne()
    @JoinColumn(name = "idState")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GameStateDb gameStateDb;

    public UsuarioDb(@NotNull String nombre, String nickname, @NotNull String email, @NotNull String password, @NotNull Integer saldo, @NotNull Integer totalApostado) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        this.totalApostado = totalApostado;
    }

    public UsuarioDb(String nombre2, String nickname2, String email2, String encode) {
        this.nombre = nombre2;
        this.nickname = nickname2;
        this.email = email2;
        this.password = encode;
    }

}
