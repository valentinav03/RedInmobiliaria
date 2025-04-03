/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RedInmobiliaria.Seguridad;

/**
 *
 * @author Karolina Aponte
 */
public class JwtResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nombreUsuario;
    private String email;
    private String rol;

    public JwtResponse(String token, Long id, String nombreUsuario, String email, String rol) {
        this.token = token;
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.rol = rol;
    }

    // Getters y setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
