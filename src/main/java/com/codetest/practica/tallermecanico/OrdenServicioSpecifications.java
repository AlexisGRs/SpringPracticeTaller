package com.codetest.practica.tallermecanico;

import com.codetest.practica.tallermecanico.model.EstadoOrden;
import com.codetest.practica.tallermecanico.model.OrdenServicio;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrdenServicioSpecifications {

    public static Specification<OrdenServicio> tieneEstado(EstadoOrden estado) {
        return (root, query, cb) ->
                estado == null ? null : cb.equal(root.get("estadoOrden"), estado);
    }

    public static Specification<OrdenServicio> tieneMecanico(Long mecanicoId) {
        return (root, query, cb) ->
                mecanicoId == null ? null : cb.equal(root.get("mecanicoAsignado").get("id"), mecanicoId);
    }

    public static Specification<OrdenServicio> tieneVehiculo(Long vehiculoId) {
        return (root, query, cb) ->
                vehiculoId == null ? null : cb.equal(root.get("vehiculo").get("id"), vehiculoId);
    }
    public static Specification<OrdenServicio> desde(LocalDate fecha) {
        return (root, query, cb) ->
                fecha == null ? null : cb.greaterThanOrEqualTo(root.get("fechaRecepcion"), fecha.atStartOfDay());
    }

    public static Specification<OrdenServicio> hasta(LocalDate fecha) {
        return (root, query, cb) ->
                fecha == null ? null : cb.lessThanOrEqualTo(root.get("fechaRecepcion"), fecha.atTime(LocalTime.MAX));
    }

}
