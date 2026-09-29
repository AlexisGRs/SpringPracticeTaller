package com.codetest.practica.tallermecanico.controller;


import com.codetest.practica.tallermecanico.dto.request.VehiculoRequest;
import com.codetest.practica.tallermecanico.dto.response.VehiculoResponse;
import com.codetest.practica.tallermecanico.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponse> obtenerVehiculo(@PathVariable Long id){
        return ResponseEntity.ok(vehiculoService.obtenerVehiculoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponse> actualizarVehiculo(@PathVariable Long id,@Valid @RequestBody VehiculoRequest vehiculoRequest){
        return ResponseEntity.ok(vehiculoService.actualizarVehiculo(id, vehiculoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id){
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }

}
