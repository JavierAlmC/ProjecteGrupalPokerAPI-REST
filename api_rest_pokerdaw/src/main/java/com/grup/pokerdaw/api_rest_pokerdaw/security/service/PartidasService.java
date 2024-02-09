package com.grup.pokerdaw.api_rest_pokerdaw.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.PartidaRepository;

import io.micrometer.common.lang.NonNull;

@Service
@Transactional
public class PartidasService {

    @Autowired
    PartidaRepository partidaRepository;

    public Optional<PartidaDb> getById(Long id) {
        return partidaRepository.findById(id);
    }

    public void save(@NonNull PartidaDb partida) {
        partidaRepository.save(partida);
    }

    public void findAll(@NonNull PartidaDb partida) {
        partidaRepository.findAll();
    }
}
