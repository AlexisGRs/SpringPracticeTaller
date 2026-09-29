package com.codetest.practica.tallermecanico.controller;


import com.codetest.practica.tallermecanico.dto.request.CambioEstadoRequest;
import com.codetest.practica.tallermecanico.dto.request.OrdenServicioRequest;
import com.codetest.practica.tallermecanico.dto.response.OrdenServicioResponse;
import com.codetest.practica.tallermecanico.model.EstadoOrden;
import com.codetest.practica.tallermecanico.service.OrdenServicioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenServicioController {

    private final OrdenServicioService ordenServicioService;

    public OrdenServicioController(OrdenServicioService ordenServicioService) {
        this.ordenServicioService = ordenServicioService;
    }


    @PostMapping
    public ResponseEntity<OrdenServicioResponse> crearOrdenServicico(@Valid @RequestBody OrdenServicioRequest ordenServicioRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenServicioService.crearOrdenServicio(ordenServicioRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicioResponse> obtenerOrdenById(@PathVariable Long id){
        return ResponseEntity.ok(ordenServicioService.obtenerOrdenById(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenServicioResponse> cambiarEstadoOrden(@PathVariable Long id, @Valid @RequestBody CambioEstadoRequest cambioEstadoRequest){
        return ResponseEntity.ok(ordenServicioService.cambiarEstado(id, cambioEstadoRequest));
    }

    @GetMapping
    public ResponseEntity<Page<OrdenServicioResponse>> buscarOrdenes(
            @RequestParam(required = false) EstadoOrden estado,
            @RequestParam(required = false) Long mecanicoId,
            @RequestParam(required = false) Long vehiculoId,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ordenServicioService.buscarOrdenes(estado, mecanicoId, vehiculoId, desde, hasta, pageable));
    }

}
