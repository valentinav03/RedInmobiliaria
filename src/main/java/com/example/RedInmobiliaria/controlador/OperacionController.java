package com.example.RedInmobiliaria.controlador;

import com.example.RedInmobiliaria.modelo.Operacion;
import com.example.RedInmobiliaria.repositorio.OperacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operaciones")
public class OperacionController {

    @Autowired
    private OperacionRepository operacionRepository;

    @GetMapping
    public List<Operacion> getAllOperaciones() {
        return operacionRepository.findAll();
    }
}

