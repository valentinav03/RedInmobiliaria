/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import com.example.RedInmobiliaria.servicio.ImagenPropiedadServicio;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<ImagenPropiedad>> getImagenesPropiedad(@PathVariable Integer idPropiedad){
        List<ImagenPropiedad> imagenes = service.getImagenesPropiedad(idPropiedad); 
        if(imagenes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(imagenes);
        }
        return ResponseEntity.ok(imagenes);
    }


    //Crear una nueva imagen
    @PostMapping("/upload")
    public ResponseEntity<?> subirImagen(@RequestParam("id_propiedad") Integer idPropiedad,
                                         @RequestParam("file") MultipartFile file){
        try{
            ImagenPropiedad imagen = service.guardarImagen(idPropiedad, file);
            return ResponseEntity.ok(imagen);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
    public ResponseEntity<Void> eliminarImagen(@PathVariable Integer id) {
        service.eliminarImagen(id);
        return ResponseEntity.noContent().build();
    }

}
