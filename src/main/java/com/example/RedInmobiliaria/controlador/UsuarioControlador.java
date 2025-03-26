/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.Usuario;
import com.example.RedInmobiliaria.servicio.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @GetMapping("/List")
    public List<Usuario> cargarProductos(){
        return usuarioServicio.getUsuario();
    }
    
    @GetMapping("/List/{id}")
    public Usuario buscarPorId(@PathVariable Long id){
        return usuarioServicio.buscarUsuario(id); 
    }
    
    @PostMapping
    public ResponseEntity<Usuario> agregar(@RequestBody Usuario usuario){
        Usuario obj = usuarioServicio.nuevoUsuario(usuario);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario obj = usuarioServicio.buscarUsuario(id);
        if (obj != null) {
            obj.setId(usuario.getId());
            obj.setNombre_usuario(usuario.getNombre_usuario());
            obj.setEmail_usuario(usuario.getEmail_usuario());
            obj.setId_tipo_usuario(usuario.getId_tipo_usuario());
            
            Usuario usuarioActualizado = usuarioServicio.editarUsuario(obj);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> eliminar(@PathVariable Long id){
        Usuario obj = usuarioServicio.buscarUsuario(id);
        if (obj!=null) {
            usuarioServicio.borrarUsuario(id);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
