package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado_propiedad") // nombre exacto de la tabla en la base de datos
public class EstadoPropiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado") // nombre exacto del campo en BD
    private Integer idEstado;

    @Column(name = "nombre_estado_propiedad") // nombre exacto del campo en BD
    private String nombreEstadoPropiedad;
}


