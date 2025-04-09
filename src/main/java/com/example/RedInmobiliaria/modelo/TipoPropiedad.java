package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_propiedad") // nombre exacto de la tabla
public class TipoPropiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo") // nombre exacto del campo en BD
    private Integer idTipo;

    @Column(name = "nombre_tipo_propiedad") // nombre exacto del campo en BD
    private String nombreTipoPropiedad;
}


