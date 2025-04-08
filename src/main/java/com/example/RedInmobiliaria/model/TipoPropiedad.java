package com.example.RedInmobiliaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TipoPropiedad")
public class TipoPropiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipo;

    private String nombreTipoPropiedad;
}

