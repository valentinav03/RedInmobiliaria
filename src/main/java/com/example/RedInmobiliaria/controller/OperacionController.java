package com.example.RedInmobiliaria.controller;

import com.example.RedInmobiliaria.model.Operacion;
import com.example.RedInmobiliaria.repository.OperacionRepository;
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

