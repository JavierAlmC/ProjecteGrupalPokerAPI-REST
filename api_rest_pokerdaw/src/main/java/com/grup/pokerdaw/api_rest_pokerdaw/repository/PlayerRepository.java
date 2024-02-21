package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PlayerDb;

public interface PlayerRepository extends JpaRepository<PlayerDb, Long>{
    
}
