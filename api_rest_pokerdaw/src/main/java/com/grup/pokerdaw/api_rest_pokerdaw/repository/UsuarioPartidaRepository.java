package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.UsuarioPartidaDb;

public interface UsuarioPartidaRepository extends JpaRepository<UsuarioPartidaDb,Long>{
    Optional<UsuarioPartidaDb> findById(Long id);
}
