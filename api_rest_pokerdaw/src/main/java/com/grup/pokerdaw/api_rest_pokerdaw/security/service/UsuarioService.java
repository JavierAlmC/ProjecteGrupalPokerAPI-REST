package com.grup.pokerdaw.api_rest_pokerdaw.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.UsuarioRepository;

import io.micrometer.common.lang.NonNull;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuaurioRepository;

    public Optional<UsuarioDb> getByNickname(String nickname) {
        return usuaurioRepository.findByNickname(nickname);
    }

    public boolean existsByNickname(String nickname) {
        return usuaurioRepository.existsByNickname(nickname);
    }

    public boolean existsByEmail(String email) {
        return usuaurioRepository.existsByEmail(email);
    }

    public void save(@NonNull UsuarioDb usuario) {
        usuaurioRepository.save(usuario);
    }
}
