package com.erismardev.apirest.mappers;

import com.erismardev.apirest.dto.UsuarioInputDTO;
import com.erismardev.apirest.dto.UsuarioResponseDTO;
import com.erismardev.apirest.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioInputDTO dto);
    UsuarioResponseDTO toDTO(Usuario entity);
}
