package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;

public interface PartidaRepository extends JpaRepository<PartidaDb, Long> {
    public Optional<PartidaDb> findById(Long id);

    public Page<PartidaDb> findByDescripcionContaining(String descripcion, Pageable pageable);
}
