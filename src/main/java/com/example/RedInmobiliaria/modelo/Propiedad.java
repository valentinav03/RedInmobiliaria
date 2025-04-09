package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "propiedad") // nombre exacto de la tabla en la base de datos
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propiedad")
    private Integer idPropiedad;

    @Column(name = "id_vendedor")
    private Integer idVendedor;

    @ManyToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private TipoPropiedad tipo;

    @ManyToOne
    @JoinColumn(name = "id_operacion", referencedColumnName = "id_operacion")
    private Operacion operacion;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    private EstadoPropiedad estado;

    @Column(name = "id_precio")
    private Double precio;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;
}


