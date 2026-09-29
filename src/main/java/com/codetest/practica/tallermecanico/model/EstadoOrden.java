package com.codetest.practica.tallermecanico.model;

public enum EstadoOrden {
    RECIBIDA,
    EN_PROCESO,
    FINALIZADA,
    ENTREGADA;

    public boolean puedeTransicionarA(EstadoOrden nuevoEstado) {
        return switch (this) {
            case RECIBIDA -> nuevoEstado == EN_PROCESO;
            case EN_PROCESO -> nuevoEstado == FINALIZADA;
            case FINALIZADA -> nuevoEstado == ENTREGADA;
            case ENTREGADA -> false;
        };
    }
}