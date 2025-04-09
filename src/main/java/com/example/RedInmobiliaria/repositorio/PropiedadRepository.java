package com.example.RedInmobiliaria.repositorio;

import com.example.RedInmobiliaria.modelo.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {
}

