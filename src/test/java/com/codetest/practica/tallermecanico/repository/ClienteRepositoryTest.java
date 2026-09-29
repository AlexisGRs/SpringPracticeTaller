package com.codetest.practica.tallermecanico.repository;

import com.codetest.practica.tallermecanico.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deberiaGuardarYEncontrarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("5512345678");
        cliente.setCorreo("juan@correo.com");
        cliente.setDireccion("Calle Falsa 123");

        Cliente guardado = clienteRepository.save(cliente);

        assertThat(guardado.getId()).isNotNull();
        assertThat(clienteRepository.findById(guardado.getId())).isPresent();
    }
}
