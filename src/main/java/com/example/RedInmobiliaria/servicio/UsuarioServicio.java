/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.Usuario;
import com.example.RedInmobiliaria.repositorio.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Karolina Aponte
 */

@Service
@Transactional
public class UsuarioServicio implements IUsuarioServicio{
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuario> getUsuario() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarUsuario(Long id) {
        Usuario usuario = null;
        usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        return usuario;
    }

    @Override
    public Usuario nuevoUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario editarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public int borrarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
        return 1;
    }
    
    @Override
    public Usuario buscarUsuarioNombre(Usuario usuario) {
        if (usuario == null || usuario.getNombre_usuario() == null) {
            return null;
        }
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByNombreUsuario(usuario.getNombre_usuario());
        return usuarioOptional.orElse(null);
    }
}
