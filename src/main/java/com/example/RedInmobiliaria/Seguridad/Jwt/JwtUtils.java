/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.Seguridad.Jwt;

import com.example.RedInmobiliaria.modelo.Usuario;
import com.example.RedInmobiliaria.repositorio.UsuarioRepositorio;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Karolina Aponte
 */

@Component
public class JwtUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
        
    @Value("${jwt.secret:}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:86400000}")
    private int jwtExpirationMs;
    
    private SecretKey secretKey;
    
    @PostConstruct
    public void init() {
        // Si no se proporciona una clave secreta en el archivo de configuración, genera una clave aleatoria
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            // Imprime la clave generada para que puedas guardarla en tu configuración
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            logger.info("Se ha generado una nueva clave JWT. Guárdala en tu archivo de configuración: {}", encodedKey);
        } else {
            // Si se proporciona una clave secreta, decodifícala de Base64
            try {
                byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
                secretKey = Keys.hmacShaKeyFor(decodedKey);
            } catch (Exception e) {
                logger.error("Error al decodificar la clave JWT. Asegúrate de que esté codificada en Base64.", e);
                // En caso de error, genera una nueva clave
                secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                logger.info("Se ha generado una nueva clave JWT. Guárdala en tu archivo de configuración: {}", encodedKey);
            }
        }
    }
    
    // Generar token JWT a partir de la autenticación
    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        // Buscar el usuario para obtener su ID
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con nombre de usuario: " + username));
        
        return Jwts.builder()
                .setSubject(username)
                .claim("id", usuario.getId()) // Añadir el ID como un claim
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(secretKey)
                .compact();
    }
    
    // Obtener la clave de firma para JWT
    private Key getSigningKey() {
        return secretKey;
    }
    
    // Extraer nombre de usuario del token JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    // Extraer ID de usuario del token JWT
    public Long getUserIdFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }
    
    // Validar el token JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Claims JWT vacío: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error al validar token JWT: {}", e.getMessage());
        }
        return false;
    }
    
    // Extraer token desde el header Authorization
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        
        return null;
    }
    
    public String generateJwtTokenWithUserDetails(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
