package com.erismardev.apirest.controllers;

import com.erismardev.apirest.dto.ProdutoInputDTO;
import com.erismardev.apirest.dto.ProdutoResponseDTO;
import com.erismardev.apirest.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto com base nos dados fornecidos")
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoInputDTO produtoInputDTO) {
        return ResponseEntity.ok(produtoService.criar(produtoInputDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente pelo ID")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoInputDTO produtoInputDTO) {
        return ResponseEntity.ok(produtoService.atualizar(id, produtoInputDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove um produto pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}