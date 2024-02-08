package com.grup.pokerdaw.api_rest_pokerdaw.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.PartidaDb;

public interface PartidaRepository extends JpaRepository<PartidaDb, Long> {
    Optional<PartidaDb> findById(Long id);
}
