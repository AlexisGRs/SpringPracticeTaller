package com.codetest.practica.tallermecanico.service;


import com.codetest.practica.tallermecanico.dto.request.VehiculoRequest;
import com.codetest.practica.tallermecanico.dto.response.VehiculoResponse;
import com.codetest.practica.tallermecanico.exception.ClienteNoEncontradoException;
import com.codetest.practica.tallermecanico.exception.VehiculoNoEncontradoException;
import com.codetest.practica.tallermecanico.exception.VehiculoTieneOrdenesActivasException;
import com.codetest.practica.tallermecanico.model.Cliente;
import com.codetest.practica.tallermecanico.model.EstadoOrden;
import com.codetest.practica.tallermecanico.model.Vehiculo;
import com.codetest.practica.tallermecanico.repository.ClienteRepository;
import com.codetest.practica.tallermecanico.repository.OrdenServicioRepository;
import com.codetest.practica.tallermecanico.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;
    private final OrdenServicioRepository ordenServicioRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository, ClienteRepository clienteRepository, OrdenServicioRepository ordenServicioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
        this.ordenServicioRepository = ordenServicioRepository;
    }

    @Transactional(readOnly = true)
    public VehiculoResponse obtenerVehiculoById(Long id){
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow( () -> new VehiculoNoEncontradoException("No existe el vehiculo con id:" + id));
        return toVehiculoResponse(vehiculo);
    }


    @Transactional(readOnly = true)
    public List<VehiculoResponse> obtenerVehiculosByCliente(Long clienteId){
        clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNoEncontradoException("No existe el cliente con id:" + clienteId));

        return vehiculoRepository.findByClientePropietarioId(clienteId).stream().map(this::toVehiculoResponse).toList();
    }

    @Transactional
    public VehiculoResponse crearVehiculo(Long clienteId, VehiculoRequest vehiculoRequest){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow( ()-> new ClienteNoEncontradoException("No existe el cliente con id:" + clienteId));

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoRequest.getMarca());
        vehiculo.setModelo(vehiculoRequest.getModelo());
        vehiculo.setYear(vehiculoRequest.getYear());
        vehiculo.setPlaca(vehiculoRequest.getPlaca());

        vehiculo.setClientePropietario(cliente);

        Vehiculo vehiculoGuardado= vehiculoRepository.save(vehiculo);

        return toVehiculoResponse(vehiculoGuardado);
    }

    @Transactional
    public VehiculoResponse actualizarVehiculo(Long id, VehiculoRequest vehiculoRequest){
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow( () -> new VehiculoNoEncontradoException("No existe el vehiculo con id:" + id));

        vehiculo.setPlaca(vehiculoRequest.getPlaca());
        vehiculo.setYear(vehiculoRequest.getYear());
        vehiculo.setModelo(vehiculoRequest.getModelo());
        vehiculo.setMarca(vehiculoRequest.getMarca());

        return toVehiculoResponse(vehiculo);
    }

    @Transactional
    public void eliminarVehiculo(Long id){
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow( () -> new VehiculoNoEncontradoException("No existe el vehiculo con id:" + id));

        if(ordenServicioRepository.existsByVehiculoIdAndEstadoOrdenNot(id, EstadoOrden.ENTREGADA)){
            throw new VehiculoTieneOrdenesActivasException("El vehiculo no se puede eliminar porque tiene ordenes activas");
        }

        vehiculo.setActivo(false);
    }

    private VehiculoResponse toVehiculoResponse(Vehiculo vehiculo) {
        return new VehiculoResponse(
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getYear(),
                vehiculo.getClientePropietario().getId()
        );
    }

}