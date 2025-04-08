package com.example.RedInmobiliaria.modelos;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Entity
@Table(name = "cita")
public class cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Usuario idCliente;

    @ManyToOne
    @JoinColumn(name = "propiedad")
    private Propiedad idPropiedad;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private static estadoCita idEstado;

    public cita(){
    }

    public cita(Integer id, estadoCita idEstado, LocalDateTime fechaHora, Integer idCliente, Integer idPropiedad){
        this.id = id;
        this.idEstado = idEstado;
        this.idCliente = idCliente;
        this.idPropiedad = idPropiedad;
        this.fechaHora = fechaHora;
    }

    public Integer getId() {return id;}
    public estadoCita getIdEstado() { return idEstado;}
    public Integer getIdCliente() {return idCliente;}
    public Integer getIdPropiedad() {return idPropiedad;}
    public LocalDateTime getFechaHora() {return fechaHora;}
    public void setId(Integer id) { this.id=id;}
    public void setIdCliente(Usuario idCliente) {this.idCliente = idCliente;}
    public void setIdPropiedad(Propiedad idPropiedad) {this.idPropiedad = idPropiedad;}
    public void setFechaHora(LocalDateTime fechaHora) {this.fechaHora = fechaHora;}
    public void setIdEstado(estadoCita idEstado) {this.idEstado = idEstado;}
}
