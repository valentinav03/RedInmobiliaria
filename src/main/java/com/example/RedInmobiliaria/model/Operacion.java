package com.example.RedInmobiliaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Operacion")
public class Operacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOperacion;

    private String nombreOperacion;
}

