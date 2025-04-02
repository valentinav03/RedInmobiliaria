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
import jakarta.persistence.Table;

/**
 *
 * @author Karolina Aponte
 */

@Entity
@Table(name = TipoUsuario.TABLE_NAME)
public class TipoUsuario {
    public static final String TABLE_NAME = "tipo_usuario";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_usuario")
    private Long id;
    
    @Column(name = "nombre_tipo_usuario")
    private String nombre_tipo_usuario;

    public TipoUsuario() {
    }

    public TipoUsuario(Long id, String nombre_tipo_usuario) {
        this.id = id;
        this.nombre_tipo_usuario = nombre_tipo_usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_tipo_usuario() {
        return nombre_tipo_usuario;
    }

    public void setNombre_tipo_usuario(String nombre_tipo_usuario) {
        this.nombre_tipo_usuario = nombre_tipo_usuario;
    }
 
}
