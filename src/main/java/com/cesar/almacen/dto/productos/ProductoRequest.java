package com.cesar.almacen.dto.productos;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import  java.math.BigDecimal;
@Schema(description = "Datos necesarios para crear o actualizar un producto")
public record ProductoRequest(

        @Schema(description = "Nombre del producto",example = "Xbox Series X")
        @NotBlank(message = "El nombre es requerido")
        @Size (min = 5, max = 30, message = "El nombre debe tener 5 y 30 caracteres")
        String nombre,
        @Schema(description = "Categoria del producto",example = "ELectronica")
        @NotBlank(message = "categoria requerida")
        String categoria,
        @Schema(description = "Precio del producto",example = "15.000")
        @NotNull(message = "precio requerido")
        @Positive(message = "debe ser positivo")
        BigDecimal precio,
        @Schema(description = "Cantidad disponible del producto",example = "100")
        @NotNull(message = "cantidad requerida")
        @Positive(message = "debe ser positivo")
        Integer cantidad
) { }
