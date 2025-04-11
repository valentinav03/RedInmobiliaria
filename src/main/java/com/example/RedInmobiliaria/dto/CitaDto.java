package com.example.RedInmobiliaria.dto;

import java.time.LocalDateTime;
public class CitaDto {
    private Long idCliente;
    private Integer idPropiedad;
    private Integer idEstado;
    private LocalDateTime fechaHora;

        public Long getIdCliente() {
            return idCliente;
        }

        public void setIdCliente(Long idCliente) {
            this.idCliente = idCliente;
        }

        public Integer getIdPropiedad() {
            return idPropiedad;
        }

        public void setIdPropiedad(Integer idPropiedad) {
            this.idPropiedad = idPropiedad;
        }

        public Integer getIdEstado() {
            return idEstado;
        }

        public void setIdEstado(Integer idEstado) {
            this.idEstado = idEstado;
        }

        public LocalDateTime getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(LocalDateTime fechaHora) {
            this.fechaHora = fechaHora;
        }
    }


