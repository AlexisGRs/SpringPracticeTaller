package com.codetest.practica.tallermecanico.repository;

import com.codetest.practica.tallermecanico.model.EstadoOrden;
import com.codetest.practica.tallermecanico.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long>, JpaSpecificationExecutor<OrdenServicio> {


    long countByMecanicoAsignadoIdAndEstadoOrden(Long mecanicoId, EstadoOrden estado);

    boolean existsByVehiculoIdAndEstadoOrdenNot(Long vehiculoId, EstadoOrden estado);

}
