package com.codetest.practica.tallermecanico.controller;


import com.codetest.practica.tallermecanico.dto.request.MecanicoRequest;
import com.codetest.practica.tallermecanico.dto.response.MecanicoResponse;
import com.codetest.practica.tallermecanico.service.MecanicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {
    private final MecanicoService mecanicoService;

    public MecanicoController(MecanicoService mecanicoService) {
        this.mecanicoService = mecanicoService;
    }

    @GetMapping
    public ResponseEntity<List<MecanicoResponse>> obtenerMecanico(){
        return ResponseEntity.ok(mecanicoService.obtenerMecanicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoResponse> obtenerMecanicoByID(@PathVariable Long id){
        return ResponseEntity.ok(mecanicoService.obtenerMecanicoById(id));
    }

    @PostMapping
    public ResponseEntity<MecanicoResponse> crearMecanico(@Valid @RequestBody MecanicoRequest mecanicoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(mecanicoService.crearMecanico(mecanicoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MecanicoResponse> actualizarMecanico(@PathVariable Long id, @Valid @RequestBody MecanicoRequest mecanicoRequest ){
        return ResponseEntity.ok(mecanicoService.actualizarMecanico(id, mecanicoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMecanico(@PathVariable Long id){
        mecanicoService.eliminarMecanico(id);
        return ResponseEntity.noContent().build();
    }


}
