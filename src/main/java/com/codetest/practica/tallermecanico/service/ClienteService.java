package com.codetest.practica.tallermecanico.service;

import com.codetest.practica.tallermecanico.dto.request.ClienteRequest;
import com.codetest.practica.tallermecanico.dto.response.ClienteResponse;
import com.codetest.practica.tallermecanico.exception.ClienteNoEncontradoException;
import com.codetest.practica.tallermecanico.exception.ClienteTieneVehiculosException;
import com.codetest.practica.tallermecanico.model.Cliente;
import com.codetest.practica.tallermecanico.repository.ClienteRepository;

import com.codetest.practica.tallermecanico.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;

    public ClienteService(ClienteRepository clienteRepository, VehiculoRepository vehiculoRepository) {
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> obtenerClientes(){
        return clienteRepository.findAll().stream().map(this::toClienteResponse).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtenerClienteByID(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow( ()-> new ClienteNoEncontradoException("No existe un cliente con el id:" + id));
        return toClienteResponse(cliente);
    }

    @Transactional
    public ClienteResponse crearCliente(ClienteRequest clienteRequest){
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRequest.getNombre());
        cliente.setCorreo(clienteRequest.getCorreo());
        cliente.setTelefono(clienteRequest.getTelefono());
        cliente.setDireccion(clienteRequest.getDireccion());

        Cliente clienteGuardao = clienteRepository.save(cliente);

        return toClienteResponse(clienteGuardao);
    }

    @Transactional
    public ClienteResponse actualizarCliente(Long id, ClienteRequest clienteRequest){
        Cliente cliente = clienteRepository.findById(id).orElseThrow( () -> new ClienteNoEncontradoException("No existe un cliente con el id:" + id));
        cliente.setNombre(clienteRequest.getNombre());
        cliente.setCorreo(clienteRequest.getCorreo());
        cliente.setDireccion(clienteRequest.getDireccion());
        cliente.setTelefono(clienteRequest.getTelefono());

        return toClienteResponse(cliente);
    }

    @Transactional
    public void eliminarCliente(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow( () -> new ClienteNoEncontradoException("No existe un cliente con el id:" + id));

        boolean tieneVehiculo = vehiculoRepository.existsByClientePropietarioId(id);

        if(tieneVehiculo){
            throw new ClienteTieneVehiculosException("No se puede eliminar el cliente con id:" + id + "porque tiene vehiculos asignados");
        }
        clienteRepository.delete(cliente);
    }

    private ClienteResponse toClienteResponse(Cliente cliente){
        return new ClienteResponse(cliente.getId(), cliente.getNombre(), cliente.getTelefono(), cliente.getCorreo(), cliente.getDireccion());
    }


}
