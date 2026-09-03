package com.cesar.almacen.dto.ventas;

import com.cesar.almacen.dto.sucursales.SucursalResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
@Schema(description = "DATOS DE VENTA")
public record VentaResponse(
        @Schema(description = "Identificador de la venta", example = "1")
        Long id,
        @Schema(description = "fecha de la venta",example = "16/02/2022")
        String fecha,
        @Schema(description = "EStado de la venta",example = "registrada")
        String estado,
        @Schema(description = "DONDE SE REALIZO LA VENTA")
        SucursalResponse sucursal,
        @Schema(description = "productos de la venta")
        List<DetalleVentaResponse>detalles,
        @Schema(description = "total monetario",example = "1500.00")
        BigDecimal total

) {
}
