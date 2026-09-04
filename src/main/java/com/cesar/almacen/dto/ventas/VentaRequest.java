package com.cesar.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Schema(description = "valores necesarios para crear ventas")
public record VentaRequest(
        @Schema(description = "id de sucursal", example = "1")
        @NotNull(message = "El id de sucrusal es requerida")
        @Positive(message = "EL id debe de ser positivo")
        Long idSucursal,

        @Schema(description = "Lista de los productos de la venta")
        @NotEmpty(message = "no puede haber lista vaciaa, productos requeridos")
        List<@Valid DetalleVentaRequest>productos
) { }
