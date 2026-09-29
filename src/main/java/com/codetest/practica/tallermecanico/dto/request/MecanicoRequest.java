package com.codetest.practica.tallermecanico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MecanicoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre tiene debe tener de 3 a 50 caracteres")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 5, max = 50, message = "La especialidad debe tener entre 5 a 50 caracteres")
    private String especialidad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
