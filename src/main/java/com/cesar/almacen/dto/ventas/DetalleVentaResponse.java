package com.cesar.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "detalle de un producto de una venta")
public record DetalleVentaResponse(
        @Schema(description = "Identificador de la venta", example = "1")
        Long idProducto,
        @Schema(description = "Nombre del producto vendido", example = "Xbox")
        String nombreProducto,
        @Schema(description = "numero de articulos vendidos", example = "3")
        Integer cantidadProducto,
        @Schema(description = "precio individual del producto", example = "15000.00")
        BigDecimal precioProducto,
        @Schema(description = "Subtotal de la venta", example = "45000.00")
        BigDecimal subTotal


) { }
