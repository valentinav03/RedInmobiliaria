package com.example.RedInmobiliaria.repositorio;

import com.example.RedInmobiliaria.modelo.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Integer> {
}

