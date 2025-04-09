package com.example.RedInmobiliaria.servicio;

import com.example.RedInmobiliaria.modelo.cita;

import java.util.List;

public interface ICitaService {

    List<cita> getCitas();

    cita nuevaCita(cita cita);

    cita buscarCita(Integer id);

    int borrarCita(Integer id);
}
