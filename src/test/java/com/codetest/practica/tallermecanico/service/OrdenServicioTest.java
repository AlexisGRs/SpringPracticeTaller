package com.codetest.practica.tallermecanico.service;

import com.codetest.practica.tallermecanico.dto.request.CambioEstadoRequest;
import com.codetest.practica.tallermecanico.dto.request.LineaServicioRequest;
import com.codetest.practica.tallermecanico.dto.request.OrdenServicioRequest;
import com.codetest.practica.tallermecanico.dto.response.OrdenServicioResponse;
import com.codetest.practica.tallermecanico.exception.LimiteOrdenesMecanicoException;
import com.codetest.practica.tallermecanico.exception.VehiculoNoEncontradoException;
import com.codetest.practica.tallermecanico.model.*;
import com.codetest.practica.tallermecanico.repository.MecanicoRepository;
import com.codetest.practica.tallermecanico.repository.OrdenServicioRepository;
import com.codetest.practica.tallermecanico.repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdenServicioServiceTest {

    @Mock
    private OrdenServicioRepository ordenServicioRepository;

    @Mock
    private MecanicoRepository mecanicoRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private OrdenServicioService ordenServicioService;

    @Test
    void deberiaCrearOrdenConCostoTotalCalculado() {
        // Arrange: preparo un vehículo y un mecánico "existentes"
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        Mecanico mecanico = new Mecanico();
        mecanico.setId(1L);

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(mecanicoRepository.findById(1L)).thenReturn(Optional.of(mecanico));

        // "guardar" simplemente devuelve el mismo objeto que le pasaron
        when(ordenServicioRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Arrange: construyo el request con 2 líneas de servicio
        LineaServicioRequest linea1 = new LineaServicioRequest();
        linea1.setDescripcion("Cambio de aceite");
        linea1.setCosto(new BigDecimal("450.00"));

        LineaServicioRequest linea2 = new LineaServicioRequest();
        linea2.setDescripcion("Revision de frenos");
        linea2.setCosto(new BigDecimal("300.00"));

        OrdenServicioRequest request = new OrdenServicioRequest();
        request.setVehiculoId(1L);
        request.setMecanicoId(1L);
        request.setLineaServicioRequests(List.of(linea1, linea2));

        // Act
        OrdenServicioResponse response = ordenServicioService.crearOrdenServicio(request);

        // Assert
        assertThat(response.getCostoTotal()).isEqualByComparingTo("750.00");
        assertThat(response.getEstadoOrden()).isEqualTo(EstadoOrden.RECIBIDA);
        assertThat(response.getLineas()).hasSize(2);
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExiste() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        OrdenServicioRequest request = new OrdenServicioRequest();
        request.setVehiculoId(99L);
        request.setMecanicoId(1L);
        request.setLineaServicioRequests(List.of());

        assertThrows(VehiculoNoEncontradoException.class,
                () -> ordenServicioService.crearOrdenServicio(request));
    }

    @Test
    void deberiaLanzarExcepcionAlSuperarLimiteDeOrdenesEnProceso() {
        Mecanico mecanico = new Mecanico();
        mecanico.setId(1L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        OrdenServicio orden = new OrdenServicio();
        orden.setId(10L);
        orden.setEstadoOrden(EstadoOrden.RECIBIDA);
        orden.setMecanicoAsignado(mecanico);
        orden.setVehiculo(vehiculo);

        when(ordenServicioRepository.findById(10L)).thenReturn(Optional.of(orden));
        when(ordenServicioRepository.countByMecanicoAsignadoIdAndEstadoOrden(1L, EstadoOrden.EN_PROCESO))
                .thenReturn(3L);

        CambioEstadoRequest cambioEstadoRequest = new CambioEstadoRequest();
        cambioEstadoRequest.setEstadoOrden(EstadoOrden.EN_PROCESO);

        assertThrows(LimiteOrdenesMecanicoException.class,
                () -> ordenServicioService.cambiarEstado(10L, cambioEstadoRequest));
    }
}