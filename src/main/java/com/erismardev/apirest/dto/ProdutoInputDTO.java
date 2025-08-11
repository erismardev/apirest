package com.erismardev.apirest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoInputDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Preço é obrigatório")
    private Double preco;
}
