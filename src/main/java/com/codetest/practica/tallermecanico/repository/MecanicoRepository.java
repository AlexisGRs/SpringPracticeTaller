package com.codetest.practica.tallermecanico.repository;

import com.codetest.practica.tallermecanico.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {
}
