/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.repositorio;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Nelson
 */
@Repository
public interface ImagenPropiedadRepositorio extends JpaRepository<ImagenPropiedad, Integer> {
	List<ImagenPropiedad> findByPropiedad_IdPropiedad(Integer idPropiedad);
}

