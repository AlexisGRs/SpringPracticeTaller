package com.codetest.practica.tallermecanico.repository;

import com.codetest.practica.tallermecanico.model.Cliente;
import com.codetest.practica.tallermecanico.model.Vehiculo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VehiculoRepositoryTest {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deberiaEncontrarVehiculosByClienteId(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("5512345678");
        cliente.setCorreo("juan@correo.com");
        cliente.setDireccion("Calle Falsa 123");

        Cliente clienteGuardado = clienteRepository.save(cliente);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setClientePropietario(clienteGuardado);
        vehiculo.setPlaca("ABC543A");
        vehiculo.setYear(2017);
        vehiculo.setModelo("Pro");
        vehiculo.setActivo(true);
        vehiculo.setMarca("Honda");

        vehiculoRepository.save(vehiculo);

        Vehiculo vehiculo_2 = new Vehiculo();
        vehiculo_2.setClientePropietario(clienteGuardado);
        vehiculo_2.setModelo("Supra");
        vehiculo_2.setActivo(true);
        vehiculo_2.setMarca("Toyota");
        vehiculo_2.setYear(2016);
        vehiculo_2.setPlaca("FGH567D");

        vehiculoRepository.save(vehiculo_2);

        List<Vehiculo> vehiculos = vehiculoRepository.findByClientePropietarioId(clienteGuardado.getId());

        assertThat(vehiculos).hasSize(2);
    }

    @Test
    void deberiaConfirmarExistenciaDeVehiculoPorCliente(){

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("5512345678");
        cliente.setCorreo("juan@correo.com");
        cliente.setDireccion("Calle Falsa 123");
        Cliente clienteGuardado = clienteRepository.save(cliente);


        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setClientePropietario(clienteGuardado);
        vehiculo.setPlaca("ABC543A");
        vehiculo.setYear(2017);
        vehiculo.setModelo("Pro");
        vehiculo.setActivo(true);
        vehiculo.setMarca("Honda");

        vehiculoRepository.save(vehiculo);


        boolean existe = vehiculoRepository.existsByClientePropietarioId(clienteGuardado.getId());

        assertThat(existe).isTrue();
    }

    @Test
    void deberiaConfirmarQueNoExisteVehiculoParaClienteSinVehiculos(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("5512345678");
        cliente.setCorreo("juan@correo.com");
        cliente.setDireccion("Calle Falsa 123");
        Cliente clienteGuardado = clienteRepository.save(cliente);

        boolean existe = vehiculoRepository.existsByClientePropietarioId(clienteGuardado.getId());

        assertThat(existe).isFalse();
    }
}
