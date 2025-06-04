/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.ImagenPropiedad;
import com.example.RedInmobiliaria.modelo.Propiedad;
import com.example.RedInmobiliaria.repositorio.ImagenPropiedadRepositorio;
import com.example.RedInmobiliaria.repositorio.PropiedadRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nelson
 */
@Service
public class ImagenPropiedadServicio implements IImagenPropiedadServicio{
    
    private final String uploadDir = "src/main/resources/static/uploads/";
    
    ImagenPropiedadRepositorio imagenPropiedadRepositorio;
    PropiedadRepository propiedadRepositorio;
    
    
    @Autowired
    public ImagenPropiedadServicio(ImagenPropiedadRepositorio imagenPropiedadRepositorio, PropiedadRepository propiedadRepositorio){
        this.imagenPropiedadRepositorio = imagenPropiedadRepositorio;
        this.propiedadRepositorio = propiedadRepositorio;
    }
    
    @Override
    public List<ImagenPropiedad> getImagenes(){
        return imagenPropiedadRepositorio.findAll();
    }
    
    @Override
    public List<ImagenPropiedad> getImagenesPropiedad(Integer idPropiedad){
        return imagenPropiedadRepositorio.findByPropiedad_IdPropiedad(idPropiedad);
    }
    
    @Override
    public ImagenPropiedad guardarImagen(Integer idPropiedad, MultipartFile file) throws IOException{
        //Validacion de tamano y formato
        if(file.getSize() > 2 * 1024 * 1024) {
            throw new IOException("La imagen es demasiado grande (Maximo 2MB)");
        }
        String nombreOrig = file.getOriginalFilename();
        String ext = nombreOrig.substring(nombreOrig.lastIndexOf('.')).toLowerCase();
        
        
        if (ext == null || (!ext.equals(".jpg") && !ext.equals(".jpeg") && !ext.equals(".png") && !ext.equals(".webp"))){
            throw new IOException("Formato no permitido (JPG, JPEG, PNG, WEBP)");
        }
        //Nombre unico
        String uniqueName = UUID.randomUUID().toString() + ext;
        Path path = Paths.get(uploadDir + uniqueName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        
        //Buscar propiedad
        Optional<Propiedad> propiedadOpt = propiedadRepositorio.findById(idPropiedad);
        if(propiedadOpt.isEmpty()){
            throw new IOException("La propiedad no existe");
        }
        
        //Guardar en BD
        ImagenPropiedad imagen = new ImagenPropiedad();
        imagen.setPropiedad(propiedadOpt.get());
        imagen.setUrlImagen("/uploads/" + uniqueName);
        return imagenPropiedadRepositorio.save(imagen);
    }
    
    @Override
    public int eliminarImagen(Integer id){
        ImagenPropiedad imagen = imagenPropiedadRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con ID: " + id));
        
        String url = imagen.getUrlImagen();
        //Borrar el archivo fisico
        if (url!=null && (url.startsWith("/uploads") || (url.startsWith("uploads")))){
            String ruta = "src/main/resources/static"+url;
            try{
                Files.deleteIfExists(Paths.get(ruta));
            } catch(IOException e) {
                System.err.println("No se pudo borrar el archivo: " + ruta);
            }
        }
        
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