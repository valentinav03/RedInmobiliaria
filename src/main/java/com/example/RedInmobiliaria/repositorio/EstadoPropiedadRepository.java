package com.example.RedInmobiliaria.repositorio;

import com.example.RedInmobiliaria.modelo.EstadoPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPropiedadRepository extends JpaRepository<EstadoPropiedad, Integer> {
}


