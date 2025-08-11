package com.erismardev.apirest.services;

import com.erismardev.apirest.dto.UsuarioInputDTO;
import com.erismardev.apirest.dto.UsuarioResponseDTO;
import com.erismardev.apirest.enums.Role;
import com.erismardev.apirest.mappers.UsuarioMapper;
import com.erismardev.apirest.repositories.UsuarioRepository;
import com.erismardev.apirest.services.exceptions.EmailAlreadyRegisteredException;
import com.erismardev.apirest.services.exceptions.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseDTO criarUsuario(UsuarioInputDTO dto, boolean isAdmin) {
        validarEmailDisponivel(dto.getEmail());

        Set<Role> roles = definirRoles(dto, isAdmin);

        var usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRoles(roles);

        var salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(salvo);
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        return usuarioMapper.toDTO(usuario);
    }

    private void validarEmailDisponivel(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado");
        }
    }

    private Set<Role> definirRoles(UsuarioInputDTO dto, boolean isAdmin) {
        if (isAdmin && dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            return dto.getRoles();
        }
        return Set.of(Role.ROLE_USER);
    }
}
