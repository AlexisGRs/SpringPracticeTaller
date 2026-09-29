package com.codetest.practica.tallermecanico.dto.response;

import java.math.BigDecimal;

public class LineaServicioResponse {

    private Long id;
    private String descripcion;
    private BigDecimal costo;

    public LineaServicioResponse(Long id, String descripcion, BigDecimal costo) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
}
