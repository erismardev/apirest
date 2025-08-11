package com.erismardev.apirest.mappers;

import com.erismardev.apirest.dto.ProdutoInputDTO;
import com.erismardev.apirest.dto.ProdutoResponseDTO;
import com.erismardev.apirest.entities.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // Sem Spring
    // Usar ProdutoMapper.INSTANCE.toDTO(produto);
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto toEntity(ProdutoInputDTO dto);

    ProdutoResponseDTO toDTO(Produto entity);

    List<ProdutoResponseDTO> toDTOList(List<Produto> produtos);
}
