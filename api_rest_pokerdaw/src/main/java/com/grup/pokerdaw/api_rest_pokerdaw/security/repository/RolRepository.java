package com.grup.pokerdaw.api_rest_pokerdaw.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.RolDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.enums.RolNombre;

public interface RolRepository extends JpaRepository<RolDb,Integer>{
    Optional<RolDb> findByNombre(RolNombre rolNombre);
    
}
