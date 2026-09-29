package com.codetest.practica.tallermecanico.service;

import com.codetest.practica.tallermecanico.OrdenServicioSpecifications;
import com.codetest.practica.tallermecanico.dto.request.CambioEstadoRequest;
import com.codetest.practica.tallermecanico.dto.request.LineaServicioRequest;
import com.codetest.practica.tallermecanico.dto.request.OrdenServicioRequest;
import com.codetest.practica.tallermecanico.dto.response.LineaServicioResponse;
import com.codetest.practica.tallermecanico.dto.response.OrdenServicioResponse;
import com.codetest.practica.tallermecanico.exception.*;
import com.codetest.practica.tallermecanico.model.*;
import com.codetest.practica.tallermecanico.repository.MecanicoRepository;
import com.codetest.practica.tallermecanico.repository.OrdenServicioRepository;
import com.codetest.practica.tallermecanico.repository.VehiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class OrdenServicioService {

    private static final int MAX_ORDENES_EN_PROCESO_POR_MECANICO = 3;
    private final OrdenServicioRepository ordenServicioRepository;
    private final MecanicoRepository mecanicoRepository;
    private final VehiculoRepository vehiculoRepository;


    public OrdenServicioService(OrdenServicioRepository ordenServicioRepository, MecanicoRepository mecanicoRepository, VehiculoRepository vehiculoRepository) {
        this.ordenServicioRepository = ordenServicioRepository;
        this.mecanicoRepository = mecanicoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional
    public OrdenServicioResponse crearOrdenServicio(OrdenServicioRequest ordenServicioRequest){
        Vehiculo vehiculo = vehiculoRepository.findById(ordenServicioRequest.getVehiculoId()).
                orElseThrow(() -> new VehiculoNoEncontradoException(
                        "No existe el vehiculo con id:" + ordenServicioRequest.getVehiculoId()));

        Mecanico mecanico = mecanicoRepository.findById(ordenServicioRequest.getMecanicoId())
                .orElseThrow( () -> new MecanicoNoEncontradoException(
                        "No existe el mecanico con id:" + ordenServicioRequest.getMecanicoId()));

        OrdenServicio ordenServicio = new OrdenServicio();

        ordenServicio.setFechaRecepcion(LocalDateTime.now());
        ordenServicio.setVehiculo(vehiculo);
        ordenServicio.setMecanicoAsignado(mecanico);
        ordenServicio.setEstadoOrden(EstadoOrden.RECIBIDA);

        BigDecimal costoTotal = BigDecimal.ZERO;

        for (LineaServicioRequest lineaServicioRequest :
                ordenServicioRequest.getLineaServicioRequests()){

            LineaServicio lineaServicio = new LineaServicio();
            lineaServicio.setCosto(lineaServicioRequest.getCosto());
            lineaServicio.setDescripcion(lineaServicioRequest.getDescripcion());
            lineaServicio.setOrdenServicio(ordenServicio);

            ordenServicio.getLineas().add(lineaServicio);


            costoTotal = costoTotal.add(lineaServicioRequest.getCosto());

        }

        ordenServicio.setCostoTotal(costoTotal);

        OrdenServicio ordenServicioGuardado = ordenServicioRepository.save(ordenServicio);

        return toOrdenServicioResponse(ordenServicioGuardado);

    }

    @Transactional(readOnly = true)
    public OrdenServicioResponse obtenerOrdenById(Long id){
        OrdenServicio ordenServicio = ordenServicioRepository.findById(id).orElseThrow(() -> new OrdenNoEncontradaException("No existe la orden con id:" + id));
        return toOrdenServicioResponse(ordenServicio);
    }

    @Transactional
    public OrdenServicioResponse cambiarEstado(Long id, CambioEstadoRequest cambioEstadoRequest){
        OrdenServicio ordenServicio = ordenServicioRepository.findById(id).orElseThrow( () -> new OrdenNoEncontradaException("No existe la orden con id:" + id));
        if(!ordenServicio.getEstadoOrden().puedeTransicionarA(cambioEstadoRequest.getEstadoOrden())){
            throw new TransicionEstadoInvalidaException("Actualmente no se puede hacer un cambio de estado");
        }

        if(cambioEstadoRequest.getEstadoOrden() == EstadoOrden.EN_PROCESO){
            if(ordenServicioRepository.countByMecanicoAsignadoIdAndEstadoOrden(ordenServicio.getMecanicoAsignado().getId(), EstadoOrden.EN_PROCESO) >= MAX_ORDENES_EN_PROCESO_POR_MECANICO){
                throw new LimiteOrdenesMecanicoException("El mecanico ha alcanzado el limite de ordenes en proceso");
            }
        }

        ordenServicio.setEstadoOrden(cambioEstadoRequest.getEstadoOrden());
        return toOrdenServicioResponse(ordenServicio);
    }

    @Transactional(readOnly = true)
    public Page<OrdenServicioResponse> buscarOrdenes(
            EstadoOrden estado,
            Long mecanicoId,
            Long vehiculoId,
            LocalDate desde,
            LocalDate hasta,
            Pageable pageable
    ) {
        Specification<OrdenServicio> specification = Specification
                .where(OrdenServicioSpecifications.tieneEstado(estado))
                .and(OrdenServicioSpecifications.tieneMecanico(mecanicoId))
                .and(OrdenServicioSpecifications.tieneVehiculo(vehiculoId))
                .and(OrdenServicioSpecifications.desde(desde))
                .and(OrdenServicioSpecifications.hasta(hasta));

        Page<OrdenServicio> ordenes =
                ordenServicioRepository.findAll(specification, pageable);

        return ordenes.map(this::toOrdenServicioResponse);
    }


    private OrdenServicioResponse toOrdenServicioResponse(OrdenServicio ordenServicio){
        List<LineaServicioResponse> lineasResponse =
                ordenServicio.getLineas()
                        .stream()
                        .map(this::toLineaServicioResponse)
                        .toList();

        return new OrdenServicioResponse(
                ordenServicio.getId(),
                ordenServicio.getFechaRecepcion(),
                ordenServicio.getEstadoOrden(),
                ordenServicio.getVehiculo().getId(),
                ordenServicio.getMecanicoAsignado().getId(),
                ordenServicio.getCostoTotal(),
                lineasResponse);
    }

    private LineaServicioResponse toLineaServicioResponse(
            LineaServicio lineaServicio
    ) {

        return new LineaServicioResponse(
                lineaServicio.getId(),
                lineaServicio.getDescripcion(),
                lineaServicio.getCosto()
        );
    }




}
