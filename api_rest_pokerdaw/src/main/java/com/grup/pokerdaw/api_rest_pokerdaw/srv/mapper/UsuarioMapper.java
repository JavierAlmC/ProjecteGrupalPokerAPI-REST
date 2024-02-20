package com.grup.pokerdaw.api_rest_pokerdaw.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.grup.pokerdaw.api_rest_pokerdaw.model.dto.UsuarioInGame;
import com.grup.pokerdaw.api_rest_pokerdaw.security.entity.UsuarioDb;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    
    @Mapping(target = "idState",source = "gameStateDb.idState")
    UsuarioInGame usuarioDbTUsuarioInGame(UsuarioDb usuarioDb);
} 
