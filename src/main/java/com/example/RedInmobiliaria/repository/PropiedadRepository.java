package com.example.RedInmobiliaria.repository;

import com.example.RedInmobiliaria.model.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {
}

