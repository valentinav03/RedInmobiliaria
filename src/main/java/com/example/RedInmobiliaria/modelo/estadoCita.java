package com.example.RedInmobiliaria.modelo;


import jakarta.persistence.*;

@Entity
@Table(name = "estado_cita")

public class estadoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_estado_cita")
    private String nombre;

    public estadoCita(){
    }

    public estadoCita(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() { return id; }

    public void setId(Integer id){ this.id = id;}

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
