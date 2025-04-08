package com.example.RedInmobiliaria.controller;

import com.example.RedInmobiliaria.model.Propiedad;
import com.example.RedInmobiliaria.model.TipoPropiedad;
import com.example.RedInmobiliaria.model.Operacion;
import com.example.RedInmobiliaria.model.EstadoPropiedad;
import com.example.RedInmobiliaria.repository.PropiedadRepository;
import com.example.RedInmobiliaria.repository.TipoPropiedadRepository;
import com.example.RedInmobiliaria.repository.OperacionRepository;
import com.example.RedInmobiliaria.repository.EstadoPropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propiedades")
@CrossOrigin(origins = "*") // Permite que el frontend acceda al backend
public class PropiedadController {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private TipoPropiedadRepository tipoPropiedadRepository;

    @Autowired
    private OperacionRepository operacionRepository;

    @Autowired
    private EstadoPropiedadRepository estadoPropiedadRepository;

    // Método para obtener todas las propiedades
    @GetMapping
    public List<Propiedad> getAllPropiedades() {
        return propiedadRepository.findAll();
    }

    // Obtener una propiedad por su ID
    @GetMapping("/{id}")
    public Propiedad getPropiedadById(@PathVariable Integer id) {
        return propiedadRepository.findById(id).orElse(null);
    }

    // Crear una nueva propiedad
    @PostMapping
    public Propiedad createPropiedad(@RequestBody Propiedad propiedad) {
        return propiedadRepository.save(propiedad);
    }

    // Actualizar una propiedad
    @PutMapping("/{id}")
    public Propiedad updatePropiedad(@PathVariable Integer id, @RequestBody Propiedad propiedadDetalles) {
        return propiedadRepository.findById(id).map(propiedad -> {
            propiedad.setIdVendedor(propiedadDetalles.getIdVendedor());
            propiedad.setTipo(propiedadDetalles.getTipo());
            propiedad.setOperacion(propiedadDetalles.getOperacion());
            propiedad.setEstado(propiedadDetalles.getEstado());
            propiedad.setIdPrecio(propiedadDetalles.getIdPrecio());
            propiedad.setDireccion(propiedadDetalles.getDireccion());
            propiedad.setLatitud(propiedadDetalles.getLatitud());
            propiedad.setLongitud(propiedadDetalles.getLongitud());
            return propiedadRepository.save(propiedad);
        }).orElse(null);
    }

    // Eliminar una propiedad por ID
    @DeleteMapping("/{id}")
    public void deletePropiedad(@PathVariable Integer id) {
        propiedadRepository.deleteById(id);
    }

    // ✅ Nuevo: Obtener todos los tipos de propiedad
    @GetMapping("/tipos")
    public List<TipoPropiedad> getAllTiposPropiedad() {
        return tipoPropiedadRepository.findAll();
    }

    // ✅ Nuevo: Obtener todas las operaciones (Venta, Arriendo, etc.)
    @GetMapping("/operaciones")
    public List<Operacion> getAllOperaciones() {
        return operacionRepository.findAll();
    }

    // ✅ Nuevo: Obtener todos los estados de propiedad (Disponible, No Disponible, etc.)
    @GetMapping("/estados")
    public List<EstadoPropiedad> getAllEstadosPropiedad() {
        return estadoPropiedadRepository.findAll();
    }
}


