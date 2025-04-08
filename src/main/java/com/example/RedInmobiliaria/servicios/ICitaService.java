package com.example.RedInmobiliaria.servicios;

import com.example.RedInmobiliaria.modelos.cita;

import java.util.List;

public interface ICitaService {

    List<cita> getCitas();

    cita nuevaCita(cita cita);

    cita buscarCita(Integer id);

    int borrarCita(Integer id);
}
