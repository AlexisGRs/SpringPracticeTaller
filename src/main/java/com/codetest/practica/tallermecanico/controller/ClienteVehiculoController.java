package com.codetest.practica.tallermecanico.controller;


import com.codetest.practica.tallermecanico.dto.request.VehiculoRequest;
import com.codetest.practica.tallermecanico.dto.response.VehiculoResponse;
import com.codetest.practica.tallermecanico.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/vehiculos")
public class ClienteVehiculoController {
    private final VehiculoService vehiculoService;

    public ClienteVehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> crearVehiculo(@PathVariable Long clienteId, @Valid @RequestBody VehiculoRequest vehiculoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crearVehiculo(clienteId, vehiculoRequest));
    }

    @GetMapping
    public  ResponseEntity<List<VehiculoResponse>> obtenerVehiculosByCliente(@PathVariable Long clienteId){
        return ResponseEntity.ok(vehiculoService.obtenerVehiculosByCliente(clienteId));
    }

}
