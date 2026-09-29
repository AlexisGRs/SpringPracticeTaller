package com.codetest.practica.tallermecanico.service;

import com.codetest.practica.tallermecanico.dto.response.ClienteResponse;
import com.codetest.practica.tallermecanico.exception.ClienteNoEncontradoException;
import com.codetest.practica.tallermecanico.model.Cliente;
import com.codetest.practica.tallermecanico.repository.ClienteRepository;
import com.codetest.practica.tallermecanico.repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deberiaObtenerClientePorId() {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("5512345678");
        cliente.setCorreo("juan@correo.com");
        cliente.setDireccion("Calle Falsa 123");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponse response = clienteService.obtenerClienteByID(1L);


        assertThat(response.getNombre()).isEqualTo("Juan Pérez");
    }

    @Test
    void deberiaLanzarExcepcionCuandoClienteNoExiste(){
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNoEncontradoException.class, () -> clienteService.obtenerClienteByID(99L));


    }
}