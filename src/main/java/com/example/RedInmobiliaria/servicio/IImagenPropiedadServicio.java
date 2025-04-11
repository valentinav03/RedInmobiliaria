/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import java.util.List;

/**
 *
 * @author Nelson
 */
public interface IImagenPropiedadServicio {
    
   //Buscar por ID de imagen
    ImagenPropiedad getImagenById(Integer id);
    
    //Listar imagenes
    List<ImagenPropiedad> getImagenes();

    //Buscar por propiedad
    List<ImagenPropiedad> getImagenesPropiedad(Integer propiedadID);
    
    //Guardar imagen
    ImagenPropiedad guardarImagen(ImagenPropiedad imagenPropiedad);
    
    //Eliminar imagen
    int eliminarImagen(Integer id);
    
    //Actualizar imagen
    ImagenPropiedad actualizarImagen(Integer id, ImagenPropiedad nuevaImagen);
}