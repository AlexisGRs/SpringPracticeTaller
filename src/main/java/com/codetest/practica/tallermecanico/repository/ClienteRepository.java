package com.codetest.practica.tallermecanico.repository;

import com.codetest.practica.tallermecanico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
