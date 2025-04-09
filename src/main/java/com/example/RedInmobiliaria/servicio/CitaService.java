package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.cita;
import com.example.RedInmobiliaria.repositorio.CitaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CitaService implements ICitaService {
    @Autowired
    CitaRepositorio citaRepositorio;

    @Override
    public List<cita> getCitas() { return citaRepositorio.findAll();}

    @Override
    public cita nuevaCita(cita cita) { return citaRepositorio.save(cita);}

    @Override
    public cita buscarCita(Integer id){
        cita cita = null;
        cita = citaRepositorio.findById(id).orElse(null);
        if (cita == null) {
            return null;
        }
        return cita;
    }

    @Override
    public int borrarCita(Integer id){
        citaRepositorio.deleteById(id);
        return 1;
    }
}
