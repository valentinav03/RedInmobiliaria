/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Karolina Aponte
 */
public interface IUsuarioServicio {
    
    //Listar Todos
    List<Usuario> getUsuario();
    
    //buscar por llaves 
    Usuario buscarUsuario(Long id);
    
    //Crear registro u objeto
    Usuario nuevoUsuario(Usuario usuario);
    
    //Actualizar registro u objeto
    Usuario editarUsuario(Usuario usuario);
    
    //Eliminar un objeto
    int borrarUsuario(Long id);
    
}
