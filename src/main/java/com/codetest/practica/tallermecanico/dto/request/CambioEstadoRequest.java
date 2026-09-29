package com.codetest.practica.tallermecanico.dto.request;

import com.codetest.practica.tallermecanico.model.EstadoOrden;
import jakarta.validation.constraints.NotNull;

public class CambioEstadoRequest {

    @NotNull(message = "El estado de la orden es obligatoria")
    private EstadoOrden estadoOrden;

    public CambioEstadoRequest() {
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }
}
