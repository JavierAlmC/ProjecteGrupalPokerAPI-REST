package com.grup.pokerdaw.api_rest_pokerdaw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.UsuarioDb;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.UsuarioRepository;

@Service
@Transactional
public class PartidasService {
    
    @Autowired
    UsuarioRepository usuaurioRepository;

    public Optional<UsuarioDb> getById(Long id){
        return usuaurioRepository.findById(id);
    }

}
