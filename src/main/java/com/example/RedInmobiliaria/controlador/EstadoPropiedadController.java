package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.EstadoPropiedad;
import com.example.RedInmobiliaria.repositorio.EstadoPropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoPropiedadController {

    @Autowired
    private EstadoPropiedadRepository estadoPropiedadRepository;

    @GetMapping
    public List<EstadoPropiedad> getAllEstados() {
        return estadoPropiedadRepository.findAll();
    }
}

