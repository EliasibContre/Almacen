package com.cesar.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Informacion de una sucursal")
public record SucursalResponse(
        @Schema(description = "indentificador d la sucursal",example = "1")
        Long id,
        @Schema(description = "Nombre de la sucursa",example = "Sucursal norte")
        String nombre,
        @Schema(description = "Nombre de la sucursal",example = "Sucursal norte")
        String direccion

) {
}
