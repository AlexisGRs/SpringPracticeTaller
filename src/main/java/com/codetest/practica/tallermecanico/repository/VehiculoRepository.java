package com.codetest.practica.tallermecanico.repository;

import com.codetest.practica.tallermecanico.model.EstadoOrden;
import com.codetest.practica.tallermecanico.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    boolean existsByClientePropietarioId(Long id);

    List<Vehiculo> findByClientePropietarioId(Long clienteId);

}
