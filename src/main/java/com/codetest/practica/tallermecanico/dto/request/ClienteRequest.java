package com.codetest.practica.tallermecanico.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre tiene debe tener de 3 a 50 caracteres")
    private String nombre;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 10, max = 12, message = "El numero de telefono debe tener de 10 a 12 numeros")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "el correo no tiene un formulario valido")
    @Size(max = 50, message = "El correo no debe superar los 50 caracteres")
    private String correo;

    @NotBlank(message = "la direccion es obligatoria")
    @Size(max = 255, message = "la direccion no puede superar los 255 caracteres")
    private String direccion;

    public ClienteRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
