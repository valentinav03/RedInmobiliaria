package com.example.RedInmobiliaria.controller;

import com.example.RedInmobiliaria.model.EstadoPropiedad;
import com.example.RedInmobiliaria.repository.EstadoPropiedadRepository;
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

