package com.cesar.almacen.controllers;

import com.cesar.almacen.dto.ventas.VentaRequest;
import com.cesar.almacen.dto.ventas.VentaResponse;
import com.cesar.almacen.services.venta.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
@Tag(name= "ventas", description = "Endpoints para la gestion de ventas")
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    @Operation(summary = "Listar ventas registradas")
    public ResponseEntity<List<VentaResponse>>listar(){
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/canceladas")
    @Operation(summary = "Listar ventas canceladas")
    public ResponseEntity<List<VentaResponse>>listarCanceladas(){
        return ResponseEntity.ok(ventaService.ListarCanceladas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta requerida por id")
    public ResponseEntity<VentaResponse>obtenerPorId(@PathVariable @Positive(message = "El id debe de ser posotivo") Long id){
        return ResponseEntity.ok(ventaService.obtenerPorIdActiva(id));
    }

    @PostMapping
    @Operation(summary = "registrar venta")
    public ResponseEntity<VentaResponse>registrar(@Valid @RequestBody VentaRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ventaService.registrar(request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Cancelar venta")
    public ResponseEntity<VentaResponse>cancelar(@PathVariable @Positive(message = "El id debe de ser positivo")Long id){
        return ResponseEntity.ok(ventaService.cancelar(id));
    }

}
