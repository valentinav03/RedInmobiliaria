/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 *
 * @author Karolina Aponte
 */
@Entity
@Table(name = Usuario.TABLE_NAME)
public class Usuario {
    public static final String TABLE_NAME = "usuario";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_usuario")
    private String nombre_usuario;
    
    @Column(name = "email_usuario")
    private String email_usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuario id_tipo_usuario;

    public Usuario() {
    }

    public Usuario(Long id, String nombre_usuario, String email_usuario, TipoUsuario id_tipo_usuario) {
        this.id = id;
        this.nombre_usuario = nombre_usuario;
        this.email_usuario = email_usuario;
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public TipoUsuario getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(TipoUsuario id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }
    
    
    
}
