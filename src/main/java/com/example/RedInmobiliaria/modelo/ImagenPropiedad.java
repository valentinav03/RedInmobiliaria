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
 * @author Nelson
 */
@Entity
@Table(name = "ImagenesPropiedad")
public class ImagenPropiedad {

    public static final String TABLE_NAME = "ImagenesPropiedad";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagen;

    @ManyToOne
    @JoinColumn(name = "id_propiedad",referencedColumnName = "id_propiedad", nullable = false)
    private Propiedad propiedad;

    @Column(name = "urlImagen", nullable = false)
    private String urlImagen;

    public ImagenPropiedad() {
    }

    public ImagenPropiedad(Integer idImagen, Propiedad propiedad, String urlImagen) {
        this.idImagen = idImagen;
        this.propiedad = propiedad;
        this.urlImagen = urlImagen;
    }

    // Getters y Setters
    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
