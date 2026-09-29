package com.codetest.practica.tallermecanico.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrdenServicioRequest {

    @NotNull(message = "Se tiene que conocer el ID del vehiculo")
    private Long vehiculoId;

    @NotNull(message = "Se tiene que conocer el ID del mecanico")
    private Long mecanicoId;

    @NotEmpty(message = "Las lineas de servicio son obligatorias")
    private List<LineaServicioRequest> lineaServicioRequests;

    public OrdenServicioRequest() {
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public Long getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(Long mecanicoId) {
        this.mecanicoId = mecanicoId;
    }

    public List<LineaServicioRequest> getLineaServicioRequests() {
        return lineaServicioRequests;
    }

    public void setLineaServicioRequests(List<LineaServicioRequest> lineaServicioRequests) {
        this.lineaServicioRequests = lineaServicioRequests;
    }
}
