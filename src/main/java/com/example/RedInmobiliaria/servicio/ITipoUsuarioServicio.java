/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.TipoUsuario;
import java.util.List;

/**
 *
 * @author Karolina Aponte
 */
public interface ITipoUsuarioServicio {
    
    //Listar Todos
    List<TipoUsuario> getTipoUsuario();
    
    //buscar por llaves 
    TipoUsuario buscarTipoUsuario(Long id);
    
}
