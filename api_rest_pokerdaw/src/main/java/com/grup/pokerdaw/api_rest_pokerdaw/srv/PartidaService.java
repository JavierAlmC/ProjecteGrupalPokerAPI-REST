package com.grup.pokerdaw.api_rest_pokerdaw.srv;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PaginaDto;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PartidaList;

import io.micrometer.common.lang.NonNull;

public interface PartidaService {

    /*public Optional<PartidaList> getById(Long id);*/

    public void save(@NonNull PartidaDb partida);

    public PaginaDto<PartidaList> findAll(Pageable paging);
}
