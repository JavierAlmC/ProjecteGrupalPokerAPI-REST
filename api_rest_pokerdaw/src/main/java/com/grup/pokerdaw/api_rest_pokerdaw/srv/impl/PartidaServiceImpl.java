package com.grup.pokerdaw.api_rest_pokerdaw.srv.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PartidaList;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.GameStateRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.repository.PartidaRepository;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.PartidaService;
import com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper.PartidaMapper;

import io.micrometer.common.lang.NonNull;

@Service
public class PartidaServiceImpl implements PartidaService {
    private final PartidaRepository partidaRepository;
    private final GameStateRepository gameStateRepository;

    public PartidaServiceImpl(PartidaRepository partidaRepository, GameStateRepository gameStateRepository) {
        this.partidaRepository = partidaRepository;
        this.gameStateRepository=gameStateRepository;
    }

    @Override
    public Optional<PartidaDb> findById(Long id) {
        Optional<PartidaDb> partida = partidaRepository.findById(id);
        if (partida.isPresent())
            return partida;
        else
            return Optional.empty();
    }

    @Override
    public PartidaDb save(@NonNull PartidaDb partida) {
        return partidaRepository.save(partida);
    }

    @Override
    public PartidaDb saveAndFlush(@NonNull PartidaDb partida) {
        return partidaRepository.saveAndFlush(partida);
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
                paginaPartidaDb.getSort());
    }

    @Override
    public PaginaDto<PartidaList> getByDescripcionContaining(String descripcion, Pageable paging) {
        Page<PartidaDb> paginaPartidaDb = partidaRepository.findByDescripcionContaining(descripcion, paging);
        return new PaginaDto<PartidaList>(
                paginaPartidaDb.getNumber(),
                paginaPartidaDb.getSize(),
                paginaPartidaDb.getTotalElements(),
                paginaPartidaDb.getTotalPages(),
                PartidaMapper.INSTANCE.partidasDbToPartidasList(paginaPartidaDb.getContent()),
                paginaPartidaDb.getSort());
    }

    @Override
    public Integer getPlayersInGame(Long idGame) {
        Optional<PartidaDb> partidaDb = partidaRepository.findById(idGame);
        if (partidaDb.isPresent())
            return partidaDb.get().getUsuarios().size();
        else
            return null;
    }

    @Override
    public boolean deleteGameById(Long idGame) {
        if (partidaRepository.findById(idGame).isPresent()) {
            gameStateRepository.deleteByPartidaDbIdGame(idGame);
            partidaRepository.deleteById(idGame);
            return true;
        } else
            return false;
    }
}
