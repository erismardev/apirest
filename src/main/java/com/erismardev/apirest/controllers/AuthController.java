package com.erismardev.apirest.controllers;

import com.erismardev.apirest.dto.*;
import com.erismardev.apirest.security.JwtUtil;
import com.erismardev.apirest.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints para autenticação e cadastro de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioService usuarioService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna um token JWT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                            content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var usuario = usuarioService.buscarPorEmail(loginDTO.getEmail());
        String token = jwtUtil.gerarToken(usuario);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário no sistema. Apenas administradores podem definir roles personalizadas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados",
                            content = @Content(schema = @Schema(implementation = UsuarioInputDTO.class)))
            }
    )
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioInputDTO dto, Authentication auth) {
        boolean isAdmin = auth != null &&
                auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        var novoUsuario = usuarioService.criarUsuario(dto, isAdmin);
        return ResponseEntity.ok(novoUsuario);
    }
}
