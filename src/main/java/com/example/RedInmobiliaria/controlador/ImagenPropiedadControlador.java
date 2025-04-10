/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import com.example.RedInmobiliaria.servicio.ImagenPropiedadServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nelson
 */
@RestController
@RequestMapping("/api/imagenPropiedad")
@CrossOrigin(origins = "*")
public class ImagenPropiedadControlador {
    
    @Autowired
    private ImagenPropiedadServicio service;
    
    //Obtener todas las imagenes
    @GetMapping
    public List<ImagenPropiedad> getImagenes(){
        return service.getImagenes();
    }
    
    //Obtener imagen con id
    @GetMapping("/{id}")
    public ImagenPropiedad getImagenById(@PathVariable Integer id){
        return service.getImagenById(id);
    }
    
    //Obtener imagenes por ID de propiedad
    @GetMapping("/propiedad/{idPropiedad}")
    public List<ImagenPropiedad> getImagenesByPropiedadId(@PathVariable Integer propiedadId){
        return service.getImagenesPropiedad(propiedadId);
    }
    
    //Crear una nueva imagen
    @PostMapping
    public ImagenPropiedad crearImagen(@RequestBody ImagenPropiedad imagen){
        return service.guardarImagen(imagen);
    }
    
    //Actualizar una imagen
    @PostMapping("/{id}")
    public ResponseEntity<ImagenPropiedad> actualizarImagen(@PathVariable Integer id, @RequestBody ImagenPropiedad imagen){
        try{
            ImagenPropiedad imagenActualizada = service.actualizarImagen(id, imagen);
            return ResponseEntity.ok(imagenActualizada);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    
    //Eliminar imagen
    @DeleteMapping("/{id}")
    public int eliminarImagen(@PathVariable Integer id){
        service.eliminarImagen(id);
        return 1;
    }
}