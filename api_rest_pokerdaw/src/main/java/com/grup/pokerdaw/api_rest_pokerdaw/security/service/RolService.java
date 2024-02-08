package com.grup.pokerdaw.api_rest_pokerdaw.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.RolDb;
import com.grup.pokerdaw.api_rest_pokerdaw.security.enums.RolNombre;
import com.grup.pokerdaw.api_rest_pokerdaw.security.repository.RolRepository;

import io.micrometer.common.lang.NonNull;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public Optional<RolDb> getByRolNombre(RolNombre RolNombre) {
        return rolRepository.findByNombre(RolNombre);
    }

    public void save(@NonNull RolDb rol) {
        rolRepository.save(rol);
    }
}
