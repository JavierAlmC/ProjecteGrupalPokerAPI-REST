package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.PartidaDb;

public interface UsuarioPartidaRepository extends JpaRepository<PartidaDb,Long>{
    Optional<PartidaDb> findById(Long id);
}
