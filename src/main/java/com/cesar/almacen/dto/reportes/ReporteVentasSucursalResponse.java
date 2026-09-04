package com.cesar.almacen.dto.reportes;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Reporte economico de ventas agrupado por sucursal")
public record ReporteVentasSucursalResponse(
        @Schema(description = "Identificador de la sucursal",example = "1")
        Long idSucursal,
        @Schema(description = "Nombre de la sucursal",example = "sucursal norte")
        String nombreSucursal,
        @Schema(description = "total facturado por ventas registradas",example = "45500.00")
        BigDecimal totalFacturado,
        @Schema(description = "Cantidad de articulos vendidos",example = "15")
        Long cantidadProductos
) {
}
