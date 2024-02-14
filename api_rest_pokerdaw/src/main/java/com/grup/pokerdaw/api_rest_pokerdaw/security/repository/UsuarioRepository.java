package com.grup.pokerdaw.api_rest_pokerdaw.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;

public interface UsuarioRepository extends JpaRepository<UsuarioDb, Long> {

    public Optional<UsuarioDb> findByNickname(String nickname);

    public boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

}
