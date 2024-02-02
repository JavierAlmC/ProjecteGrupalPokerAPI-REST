package com.grup.pokerdaw.api_rest_pokerdaw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.RolDb;
import com.grup.pokerdaw.api_rest_pokerdaw.enums.RolNombre;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.RolRepository;

import io.micrometer.common.lang.NonNull;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public Optional<RolDb> getByRolNombre(RolNombre RolNombre){
        return rolRepository.findByNombre(RolNombre);
    }

    public void save(@NonNull RolDb rol){
    rolRepository.save(rol);
    }
}
