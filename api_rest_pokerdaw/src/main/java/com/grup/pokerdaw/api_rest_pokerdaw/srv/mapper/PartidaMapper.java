package com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.PartidaDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.PartidaList;

@Mapper
public interface PartidaMapper {
    PartidaMapper INSTANCE = Mappers.getMapper(PartidaMapper.class);

    PartidaList partidaDbToPartidaList (PartidaDb partidaDb);
    List<PartidaList> partidasDbToPartidasList(List<PartidaDb> partdasDb);

}
