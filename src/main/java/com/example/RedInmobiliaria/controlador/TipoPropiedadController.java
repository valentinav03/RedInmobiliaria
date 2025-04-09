package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.TipoPropiedad;
import com.example.RedInmobiliaria.repositorio.TipoPropiedadRepository;
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
