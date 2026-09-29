package com.codetest.practica.tallermecanico.dto.request;

import jakarta.validation.constraints.*;

public class VehiculoRequest {

    @NotBlank(message = "las placas son obligatorias")
    @Size(max = 15, message = "Las placas no deben superar los 15 caracteres")
    private String placa;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no debe superar los 50 caracteres")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no debe superar los 50 caracteres")
    private String modelo;

    @NotNull(message = "el año es obligatorio")
    @Min(value = 1900, message = "El año debe tener 4 dígitos")
    @Max(value = 2100, message = "El año no puede tener más de 4 dígitos")
    private Integer year;

    public VehiculoRequest() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
