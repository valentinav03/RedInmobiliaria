/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.RedInmobiliaria.repositorio;

import com.example.RedInmobiliaria.modelo.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Karolina Aponte
 */
public interface TipoUsuarioRepositorio extends JpaRepository<TipoUsuario, Long>{
    
}
