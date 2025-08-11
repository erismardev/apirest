package com.erismardev.apirest.security;

import com.erismardev.apirest.dto.UsuarioResponseDTO;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private Long jwtExpirationMs;

    public String gerarToken(UsuarioResponseDTO usuario) {
        String roles = usuario.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailDoToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Erro ao validar token JWT: {}", e.getMessage(), e);
        }
        return false;
    }
}
