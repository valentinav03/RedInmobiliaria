package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Propiedad")
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPropiedad;

    private Integer idVendedor; // No hay relación porque no manejamos usuarios

    @ManyToOne
    @JoinColumn(name = "idTipo", referencedColumnName = "idTipo")
    private TipoPropiedad tipo;

    @ManyToOne
    @JoinColumn(name = "idOperacion", referencedColumnName = "idOperacion")
    private Operacion operacion;

    @ManyToOne
    @JoinColumn(name = "idEstado", referencedColumnName = "idEstado")
    private EstadoPropiedad estado;

    private Double idPrecio;
    private String direccion;
    private Double latitud;
    private Double longitud;
}

