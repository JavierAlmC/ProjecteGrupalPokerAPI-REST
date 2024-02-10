package com.grup.pokerdaw.api_rest_pokerdaw.srv.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PartidaList;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.PartidaRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.PartidaService;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper.PartidaMapper;

import io.micrometer.common.lang.NonNull;

@Service
public class PartidaServiceImpl implements PartidaService{
    private final PartidaRepository partidaRepository;

    public PartidaServiceImpl(PartidaRepository partidaRepository){
        this.partidaRepository=partidaRepository;
    }

    /*@Override
    public Optional<PartidaList> getById(Long id) {
        return partidaRepository.findById(id);
    }*/
    @Override
    public void save(@NonNull PartidaDb partida) {
        partidaRepository.save(partida);
    }
    @Override
    public PaginaDto<PartidaList> findAll(Pageable paging) {
        Page<PartidaDb> paginaPartidaDb = partidaRepository.findAll(paging);
        return new PaginaDto<PartidaList>(
            paginaPartidaDb.getNumber(),
            paginaPartidaDb.getSize(),
            paginaPartidaDb.getTotalElements(),
            paginaPartidaDb.getTotalPages(),
            PartidaMapper.INSTANCE.partidasDbToPartidasList(paginaPartidaDb.getContent()),
            paginaPartidaDb.getSort()
        );
    }
    @Override
    public PaginaDto<PartidaList> getByDescripcionContaining(String descripcion, Pageable paging){
        Page<PartidaDb> paginaPartidaDb = partidaRepository.findByDescripcionContaining(descripcion, paging);
        return new PaginaDto<PartidaList>(
            paginaPartidaDb.getNumber(),
            paginaPartidaDb.getSize(),
            paginaPartidaDb.getTotalElements(),
            paginaPartidaDb.getTotalPages(),
            PartidaMapper.INSTANCE.partidasDbToPartidasList(paginaPartidaDb.getContent()),
            paginaPartidaDb.getSort()
        );
    }

}
