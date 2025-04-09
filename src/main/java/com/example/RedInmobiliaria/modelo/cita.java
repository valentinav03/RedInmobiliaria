package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.*;

import java.time.LocalDateTime;


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
    @JoinColumn(name = "id_propiedad")
    private Propiedad idPropiedad;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private estadoCita idEstado;

    public cita(){
    }

    public cita(Integer id, estadoCita idEstado, LocalDateTime fechaHora, Usuario idCliente, Propiedad idPropiedad){
        this.id = id;
        this.idEstado = idEstado;
        this.idCliente = idCliente;
        this.idPropiedad = idPropiedad;
        this.fechaHora = fechaHora;
    }

    public Integer getId() {return id;}
    public estadoCita getIdEstado() { return idEstado;}
    public Usuario getIdCliente() {return idCliente;}
    public Propiedad getIdPropiedad() {return idPropiedad;}
    public LocalDateTime getFechaHora() {return fechaHora;}
    public void setId(Integer id) { this.id=id;}
    public void setIdCliente(Usuario idCliente) {this.idCliente = idCliente;}
    public void setIdPropiedad(Propiedad idPropiedad) {this.idPropiedad = idPropiedad;}
    public void setFechaHora(LocalDateTime fechaHora) {this.fechaHora = fechaHora;}
    public void setIdEstado(estadoCita idEstado) {this.idEstado = idEstado;}
}
