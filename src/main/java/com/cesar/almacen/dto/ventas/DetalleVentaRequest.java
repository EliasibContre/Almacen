package com.cesar.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
@Schema(description = "Detalles de los productos de la venta")
public record DetalleVentaRequest(
        @Schema(description = "Id del producto", example = "1")
        @NotNull(message = "el id es requerido")
        @Positive(message = "el id debe de ser positivo")
    Long idProducto,
        @Schema(description = "cantidad de producto", example = "100")
        @NotNull(message = "la cantidad es requerida")
        @Positive(message = "LA CANTIDAD DEBE SER POSITIVA")
    Integer cantidadProducto
) {}
