/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.TipoUsuario;
import com.example.RedInmobiliaria.servicio.TipoUsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Karolina Aponte
 */
@RestController
@RequestMapping("/api/tipoUsuario")
public class TipoUsuarioControlador {
    
    @Autowired
    TipoUsuarioServicio tipoUsuarioServicio;
    
    @GetMapping("/list")
    public List<TipoUsuario> cargarProductos(){
        return tipoUsuarioServicio.getTipoUsuario();
    }
    
    @GetMapping("/list/{id}")
    public TipoUsuario buscarPorId(@PathVariable Long id){
        return tipoUsuarioServicio.buscarTipoUsuario(id);
    }
}
