package com.codetest.practica.tallermecanico.controller;


import com.codetest.practica.tallermecanico.dto.request.ClienteRequest;
import com.codetest.practica.tallermecanico.dto.response.ClienteResponse;
import com.codetest.practica.tallermecanico.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>>  obtenerClientes(){
        return ResponseEntity.ok(clienteService.obtenerClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerClienteByID(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.obtenerClienteByID(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest clienteRequest){
        ClienteResponse clienteResponse = clienteService.crearCliente(clienteRequest);
        return  ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarCliente(@PathVariable Long id,@Valid @RequestBody ClienteRequest clienteRequest){
        return  ResponseEntity.ok(clienteService.actualizarCliente(id,clienteRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }



}
