package com.grup.pokerdaw.api_rest_pokerdaw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.PartidaRepository;

@Service
@Transactional
public class PartidasService {
    
    @Autowired
    PartidaRepository usuauriPartidaRepository;

    public Optional<PartidaDb> getById(Long id){
        return usuauriPartidaRepository.findById(id);
    }

}
