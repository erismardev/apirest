package com.erismardev.apirest.dto;

import com.erismardev.apirest.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioInputDTO {
    private String email;
    private String senha;
    private Set<Role> roles;
}
