package com.example.RedInmobiliaria.controlador;


import com.example.RedInmobiliaria.dto.CitaDto;
import com.example.RedInmobiliaria.modelo.cita;
import com.example.RedInmobiliaria.repositorio.EstadoCitaRepositorio;
import com.example.RedInmobiliaria.repositorio.PropiedadRepository;
import com.example.RedInmobiliaria.repositorio.UsuarioRepositorio;
import com.example.RedInmobiliaria.servicio.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class citaController {
    @Autowired
    CitaService citaService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private EstadoCitaRepositorio estadoCitaRepositorio;

    @GetMapping("/list")
    public List<cita> cargarUsuarios(){
        return citaService.getCitas();
    }

    @GetMapping("/list/{id}")
    public cita buscarPorId(@PathVariable Integer id){
        return citaService.buscarCita(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<cita> editar(@PathVariable Integer id, @RequestBody cita cita){
        cita obj = citaService.buscarCita(id);
        if(obj != null) {
            obj.setIdEstado(cita.getIdEstado());
            obj.setId(cita.getId());
            obj.setIdCliente(cita.getIdCliente());
            obj.setFechaHora(cita.getFechaHora());
            obj.setIdPropiedad(cita.getIdPropiedad());
            citaService.nuevaCita(obj);
        }else {
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<cita> agregar(@RequestBody CitaDto citaDto) {
        cita nueva = new cita();
        nueva.setIdCliente(usuarioRepositorio.findById(citaDto.getIdCliente()).orElseThrow());
        nueva.setIdPropiedad(propiedadRepository.findById(citaDto.getIdPropiedad()).orElseThrow());
        nueva.setIdEstado(estadoCitaRepositorio.findById(citaDto.getIdEstado()).orElseThrow());
        nueva.setFechaHora(citaDto.getFechaHora());

        cita obj = citaService.nuevaCita(nueva);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<cita> eliminar(@PathVariable Integer id){
        cita obj = citaService.buscarCita(id);
        if(obj != null){
            citaService.borrarCita(id);
        }else {
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
