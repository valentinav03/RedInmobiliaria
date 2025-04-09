/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.Seguridad.Jwt.JwtUtils;
import com.example.RedInmobiliaria.modelo.TipoUsuario;
import com.example.RedInmobiliaria.modelo.Usuario;
import com.example.RedInmobiliaria.repositorio.UsuarioRepositorio;
import com.example.RedInmobiliaria.servicio.UsuarioServicio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Karolina Aponte
 */

@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/list")
    public List<Usuario> cargarProductos(){
        return usuarioServicio.getUsuario();
    }
    
    @GetMapping("/list/{id}")
    public Usuario buscarPorId(@PathVariable Long id){
        return usuarioServicio.buscarUsuario(id); 
    }
    
    @GetMapping("/list/nombre/{nombreUsuario}")
    public Usuario buscarPorNombre(@PathVariable String nombreUsuario){
        
        Usuario usuario = new Usuario();
        usuario.setNombre_usuario(nombreUsuario);
        return usuarioServicio.buscarUsuarioNombre(usuario);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> agregar(@RequestBody Usuario usuario){
        Usuario obj = usuarioServicio.nuevoUsuario(usuario);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    //Actualizar
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, Authentication authentication) {
        Usuario usuario = usuarioServicio.buscarUsuario(id);
        if (usuario != null) {
            
            // Verificar que el usuario autenticado sea el mismo que se está actualizando
            if (!authentication.getName().equals(usuario.getNombre_usuario())) {
                return new ResponseEntity<>("Solo puedes editar tu propio perfil", HttpStatus.FORBIDDEN);
            }

            boolean nombreUsuarioCambiado = false;
            String nombreUsuarioOriginal = usuario.getNombre_usuario();
        
            // Actualizar solo los campos presentes en la solicitud
            if (campos.containsKey("nombre_usuario")) {
                String nuevoNombre = (String) campos.get("nombre_usuario");
                if (!nuevoNombre.equals(nombreUsuarioOriginal)) {
                    nombreUsuarioCambiado = true;
                    usuario.setNombre_usuario(nuevoNombre);
                }
            }
            
            /*if (campos.containsKey("nombre_usuario")) {
                usuario.setNombre_usuario((String) campos.get("nombre_usuario"));
            }*/ 

            if (campos.containsKey("email_usuario")) {
                usuario.setEmail_usuario((String) campos.get("email_usuario"));
            }

            if (campos.containsKey("password") && campos.containsKey("newPassword")) {
                String passwordActual = (String) campos.get("password");
                String newPassword = (String) campos.get("newPassword");
                
                if (newPassword != null && !newPassword.isEmpty() && passwordActual != null && !passwordActual.isEmpty()) {
                    
                    if (passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            
                        usuario.setPassword(passwordEncoder.encode(newPassword));

                    } else {
                        throw new RuntimeException("La contraseña actual no es correcta");
                    }
                    
                }
            }

            if (campos.containsKey("id_tipo_usuario")) {
                // Asumiendo que id_tipo_usuario es un número o un objeto con un campo id
                Object tipoUsuarioObj = campos.get("id_tipo_usuario");
                if (tipoUsuarioObj instanceof Map) {
                    Map<String, Object> tipoUsuarioMap = (Map<String, Object>) tipoUsuarioObj;
                    if (tipoUsuarioMap.containsKey("id")) {
                        Integer tipoUsuarioId = Integer.valueOf(tipoUsuarioMap.get("id").toString());
                        TipoUsuario tipoUsuario = new TipoUsuario();
                        tipoUsuario.setId(tipoUsuarioId);
                        usuario.setId_tipo_usuario(tipoUsuario);
                    }
                }
            }

            Usuario usuarioActualizado = usuarioServicio.editarUsuario(usuario);
            
            // Si el nombre de usuario cambió, generar un nuevo token
            if (nombreUsuarioCambiado) {
                UserDetails userDetails = usuarioRepositorio.findByNombreUsuario(usuarioActualizado.getNombre_usuario())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Generar el nuevo token
                String nuevoToken = jwtUtils.generateJwtTokenWithUserDetails(userDetails);
                // Crear una respuesta con el usuario actualizado y el nuevo token
                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("usuario", usuarioActualizado);
                respuesta.put("token", nuevoToken);
                respuesta.put("mensaje", "Nombre de usuario actualizado. Por favor, utiliza el nuevo token.");

                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            }
            
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Map<String, String> request){
        
        Usuario usuario = usuarioServicio.buscarUsuario(id);
        
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } 
        
        String password = request.get("password");

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
        }
        
        usuarioServicio.borrarUsuario(id);
        
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }
}
