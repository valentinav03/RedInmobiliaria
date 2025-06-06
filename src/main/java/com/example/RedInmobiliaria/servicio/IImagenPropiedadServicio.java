/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
    List<ImagenPropiedad> getImagenesPropiedad(Integer idPropiedad);
    
    //Guardar imagen
    ImagenPropiedad guardarImagen(Integer idPropiedad, MultipartFile file) throws IOException;
    
    //Eliminar imagen
    int eliminarImagen(Integer id);
    
    //Actualizar imagen
    ImagenPropiedad actualizarImagen(Integer id, ImagenPropiedad nuevaImagen);
}