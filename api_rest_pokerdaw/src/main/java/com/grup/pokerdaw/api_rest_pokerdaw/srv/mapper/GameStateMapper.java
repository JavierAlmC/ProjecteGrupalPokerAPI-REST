package com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;
import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.GameStateList;

@Mapper
public interface GameStateMapper {
    GameStateMapper INSTANCE = Mappers.getMapper(GameStateMapper.class);

    GameStateList gameStateDbToGameStateList(GameStateDb gameStateDb);

    List<GameStateList> gameStatesDbToGameStatesList(List<GameStateDb> gameStatesDb);
}
