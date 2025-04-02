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
    @Column(name = "id_usuario")
    private Long id;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "email_usuario")
    private String email_usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuario id_tipo_usuario;
    
    @Column(name = "password")
    private String password;

    public Usuario() {
    }

    public Usuario(Long id, String nombreUsuario, String email_usuario, TipoUsuario id_tipo_usuario, String password) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email_usuario = email_usuario;
        this.id_tipo_usuario = id_tipo_usuario;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_usuario() {
        return nombreUsuario;
    }

    public void setNombre_usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
