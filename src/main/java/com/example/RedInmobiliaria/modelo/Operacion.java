package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "operacion") // nombre exacto de la tabla en la base de datos
public class Operacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operacion") // nombre exacto del campo en BD
    private Integer idOperacion;

    @Column(name = "nombre_operacion") // nombre exacto del campo en BD
    private String nombreOperacion;
}


