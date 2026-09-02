package com.cesar.almacen.controllers;

import com.cesar.almacen.dto.sucursales.SucursalRequest;
import com.cesar.almacen.dto.sucursales.SucursalResponse;
import com.cesar.almacen.services.sucursal.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@AllArgsConstructor
@Validated
@Tag(name = "sucursal",description = "Endpoints para la gestion de sucursal")
public class SucursalController {
    private final SucursalService sucursalService;
    @GetMapping
    @Operation(
            summary = "Listar sucursales",
            tags = {"sucursales-Consulta"}
    )
    public ResponseEntity<List<SucursalResponse>>listar(){
        return ResponseEntity.ok(sucursalService.listar());
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener sucursal por id",
            tags = {"sucursal-Consulta"}
    )
    public ResponseEntity<SucursalResponse>obtenerPorId(
            @PathVariable @Positive(message = "El id debe ser positivo")Long id
    ){
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }
    @PostMapping
    @Operation(
            summary = "Registrar Sucursal",
            tags = {"sucursal-Consulta"}
    )
    public ResponseEntity<SucursalResponse>registrar(
            @Valid  @RequestBody SucursalRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar  sucursal",tags = {"Sucursales - Gestion"})

    public ResponseEntity <SucursalResponse> actualizar
            (@Valid @RequestBody SucursalRequest request, @PathVariable @Positive(message = "EL valor debe de ser positivo") Long id)
    {
        return  ResponseEntity.ok(sucursalService.actualizar(request,id));
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar sucursal por id",
            tags = {"Sucursal-Consulta"}
    )
    public ResponseEntity<Void>eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo")Long id
    ){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
