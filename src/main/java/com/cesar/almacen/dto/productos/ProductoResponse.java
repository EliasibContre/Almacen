package com.cesar.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductoResponse (
        @Schema(description = "indentificador del producto",example = "1")
        Long id,
        @Schema(description = "Nombre del producto",example = "Xbox Series X")
        String nombre,
        @Schema(description = "Categoria del producto",example = "ELectronica")
        String categoria,
        @Schema(description = "Precio del producto",example = "15.000")
        BigDecimal precio,
        @Schema(description = "Cantidad disponible del producto",example = "100")
        Integer cantidad
){ }
