package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.UsuarioDb;


public interface UsuarioRepository extends JpaRepository<UsuarioDb,Long>{

    static Optional<UsuarioDb> findByNickname(String nickname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByNickname'");
    }
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);


}


