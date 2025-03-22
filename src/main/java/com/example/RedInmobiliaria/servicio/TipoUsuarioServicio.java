/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.TipoUsuario;
import com.example.RedInmobiliaria.repositorio.TipoUsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Karolina Aponte
 */

@Service
@Transactional
public class TipoUsuarioServicio implements ITipoUsuarioServicio{

    
    @Autowired
    TipoUsuarioRepositorio tipoUsuarioRepositorio;
    
    @Override
    public List<TipoUsuario> getTipoUsuario() {
        return tipoUsuarioRepositorio.findAll();
    }

    @Override
    public TipoUsuario buscarTipoUsuario(Long id) {
        TipoUsuario tipoUsuario = null;
        tipoUsuario = tipoUsuarioRepositorio.findById(id).orElse(null);
        if (tipoUsuario == null) {
            return null;
        }
        return tipoUsuario;
    }
    
}
