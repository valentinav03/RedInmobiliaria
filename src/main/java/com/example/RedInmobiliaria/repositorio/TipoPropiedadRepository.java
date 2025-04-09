package com.example.RedInmobiliaria.repositorio;

import com.example.RedInmobiliaria.modelo.TipoPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPropiedadRepository extends JpaRepository<TipoPropiedad, Integer> {
}


