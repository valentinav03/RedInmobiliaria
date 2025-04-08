package com.example.RedInmobiliaria.controller;

import com.example.RedInmobiliaria.model.TipoPropiedad;
import com.example.RedInmobiliaria.repository.TipoPropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoPropiedadController {

    @Autowired
    private TipoPropiedadRepository tipoPropiedadRepository;

    @GetMapping
    public List<TipoPropiedad> getAllTipos() {
        return tipoPropiedadRepository.findAll();
    }
}
