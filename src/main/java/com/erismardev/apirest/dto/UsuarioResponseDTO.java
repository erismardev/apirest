package com.erismardev.apirest.dto;

import com.erismardev.apirest.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private Set<Role> roles;
}
