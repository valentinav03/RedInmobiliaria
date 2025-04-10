/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import com.example.RedInmobiliaria.repositorio.ImagenPropiedadRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nelson
 */
@Service
public class ImagenPropiedadServicio implements IImagenPropiedadServicio{
    
    @Autowired
    ImagenPropiedadRepositorio imagenPropiedadRepositorio;
    
    @Override
    public List<ImagenPropiedad> getImagenes(){
        return imagenPropiedadRepositorio.findAll();
    }
    
    @Override
    public List<ImagenPropiedad> getImagenesPropiedad(Integer propiedadID){
        return imagenPropiedadRepositorio.findByPropiedadId(propiedadID);
    }
    
    @Override
    public ImagenPropiedad guardarImagen(ImagenPropiedad imagenPropiedad){
        return imagenPropiedadRepositorio.save(imagenPropiedad);
    }
    
    @Override
    public int eliminarImagen(Integer id){
        imagenPropiedadRepositorio.deleteById(id);
        return 1;
    }
    
    @Override
    public ImagenPropiedad getImagenById(Integer id){
        return imagenPropiedadRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException ("Imagen no encontrada por ID: " + id));
    }
    
    
    @Override
    public ImagenPropiedad actualizarImagen(Integer id, ImagenPropiedad nuevaImagen){
        ImagenPropiedad imagenExistente = imagenPropiedadRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada por ID: "+id));
        imagenExistente.setUrlImagen(nuevaImagen.getUrlImagen());
        imagenExistente.setPropiedad(nuevaImagen.getPropiedad());
        
        return imagenPropiedadRepositorio.save(imagenExistente);
    }
    
}