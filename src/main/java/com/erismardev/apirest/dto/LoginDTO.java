package com.erismardev.apirest.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String senha;
}