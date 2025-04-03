/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.Seguridad.controlador;

import com.example.RedInmobiliaria.Seguridad.Jwt.JwtUtils;
import com.example.RedInmobiliaria.Seguridad.JwtResponse;
import com.example.RedInmobiliaria.Seguridad.LoginRequest;
import com.example.RedInmobiliaria.Seguridad.RegisterRequest;
import com.example.RedInmobiliaria.modelo.TipoUsuario;
import com.example.RedInmobiliaria.modelo.Usuario;
import com.example.RedInmobiliaria.repositorio.TipoUsuarioRepositorio;
import com.example.RedInmobiliaria.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Karolina Aponte
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    TipoUsuarioRepositorio tipoUsuarioRepositorio;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Autenticar usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNombreUsuario(), loginRequest.getPassword()));

        // Establecer autenticación en el contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generar token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        // Obtener detalles del usuario
        Usuario userDetails = (Usuario) authentication.getPrincipal();    
        
        // Construir respuesta
        return ResponseEntity.ok(new JwtResponse(jwt,
                                 userDetails.getId(), 
                                 userDetails.getUsername(), 
                                 userDetails.getEmail_usuario(),
                                 userDetails.getId_tipo_usuario().getNombre_tipo_usuario()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Verificar si el nombre de usuario ya existe
        if (usuarioRepositorio.findByNombreUsuario(registerRequest.getNombreUsuario()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: ¡El nombre de usuario ya está en uso!");
        }

        // Buscar tipo de usuario
        TipoUsuario tipoUsuario = tipoUsuarioRepositorio.findById(registerRequest.getTipoUsuarioId())
                .orElseThrow(() -> new RuntimeException("Error: Tipo de usuario no encontrado."));
                
        // Crear nueva cuenta de usuario
        Usuario usuario = new Usuario();
        usuario.setNombre_usuario(registerRequest.getNombreUsuario());
        usuario.setEmail_usuario(registerRequest.getEmail());
        usuario.setPassword(encoder.encode(registerRequest.getPassword()));
        usuario.setId_tipo_usuario(tipoUsuario);
        
        usuarioRepositorio.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente!");
    }
}
