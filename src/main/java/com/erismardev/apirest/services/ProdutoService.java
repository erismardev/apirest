package com.erismardev.apirest.services;

import com.erismardev.apirest.dto.ProdutoInputDTO;
import com.erismardev.apirest.dto.ProdutoResponseDTO;
import com.erismardev.apirest.entities.Produto;
import com.erismardev.apirest.mappers.ProdutoMapper;
import com.erismardev.apirest.repositories.ProdutoRepository;
import com.erismardev.apirest.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoMapper.toDTOList(produtos);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
        return produtoMapper.toDTO(produto);
    }

    public ProdutoResponseDTO criar(ProdutoInputDTO produtoInputDTO) {
        Produto produto = produtoMapper.toEntity(produtoInputDTO);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(salvo);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoInputDTO produtoInputDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));

        // Atualiza os campos da entidade com os dados do DTO
        produtoExistente.setNome(produtoInputDTO.getNome());
        produtoExistente.setPreco(produtoInputDTO.getPreco());

        Produto atualizado = produtoRepository.save(produtoExistente);
        return produtoMapper.toDTO(atualizado);
    }

    public void deletar(Long id) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));

        produtoRepository.delete(produtoExistente);
    }
}
