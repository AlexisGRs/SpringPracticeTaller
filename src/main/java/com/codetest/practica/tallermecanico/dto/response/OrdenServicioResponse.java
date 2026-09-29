package com.codetest.practica.tallermecanico.dto.response;

import com.codetest.practica.tallermecanico.model.EstadoOrden;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrdenServicioResponse {

    private Long id;
    private LocalDateTime fechaRecepcion;
    private EstadoOrden estadoOrden;
    private Long vehiculoId;
    private Long mecanicoId;
    private BigDecimal costoTotal;
    private List<LineaServicioResponse> lineas;

    public OrdenServicioResponse(Long id, LocalDateTime fechaRecepcion, EstadoOrden estadoOrden, Long vehiculoId, Long mecanicoId, BigDecimal costoTotal, List<LineaServicioResponse> lineas) {
        this.id = id;
        this.fechaRecepcion = fechaRecepcion;
        this.estadoOrden = estadoOrden;
        this.vehiculoId = vehiculoId;
        this.mecanicoId = mecanicoId;
        this.costoTotal = costoTotal;
        this.lineas = lineas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
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

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public List<LineaServicioResponse> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaServicioResponse> lineas) {
        this.lineas = lineas;
    }
}
