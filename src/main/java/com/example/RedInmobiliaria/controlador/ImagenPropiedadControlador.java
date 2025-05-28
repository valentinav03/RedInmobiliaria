/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import com.example.RedInmobiliaria.repositorio.ImagenPropiedadRepositorio;
import com.example.RedInmobiliaria.repositorio.PropiedadRepository;
import com.example.RedInmobiliaria.servicio.ImagenPropiedadServicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ImagenPropiedadRepositorio imagenPropiedadRepositorio;

    @Autowired
    private PropiedadRepository propiedadRepository;

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
    public ResponseEntity<String> getImagenesPropiedad(@PathVariable Integer idPropiedad) {
        List<ImagenPropiedad> imagenes = imagenPropiedadRepositorio.findByPropiedad_IdPropiedad(idPropiedad);
        if (imagenes.isEmpty()) {
            return new ResponseEntity<>("URL_NO_DISPONIBLE", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(imagenes.get(0).getUrlImagen());
    }


    //Crear una nueva imagen
    @PostMapping("/upload")
    public ResponseEntity<String> subirImagen(@RequestParam("file") MultipartFile file) {
        try {
            String nombreArchivo = file.getOriginalFilename();
            Path rutaDestino = Paths.get("src/main/resources/static/uploads/" + nombreArchivo); // actualiza con tu ruta real

            Files.copy(file.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

            String urlPublica = "http://localhost:8094/uploads/" + nombreArchivo;
            return ResponseEntity.ok(urlPublica);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @PostMapping
    public ResponseEntity<ImagenPropiedad> crearImagen(@RequestBody ImagenPropiedad imagen) {
        ImagenPropiedad guardada = service.guardarImagen(imagen);
        return ResponseEntity.ok(guardada);
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
    public ResponseEntity<ImagenPropiedad> eliminarImagen(@PathVariable Integer id) {
        service.eliminarImagen(id);
        return ResponseEntity.noContent().build();
    }

}
