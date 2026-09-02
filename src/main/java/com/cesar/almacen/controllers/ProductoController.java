package com.cesar.almacen.controllers;

import com.cesar.almacen.dto.productos.ProductoRequest;
import com.cesar.almacen.dto.productos.ProductoResponse;
import com.cesar.almacen.services.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
@Validated
@Tag(name = "Productos",description = "Endpoints para la gestion de productos")
public class ProductoController {
    private final ProductoService productoService;
    @GetMapping
    @Operation(
            summary = "Listar Productos",
            tags = {"Productos-Consulta"}
    )
    public ResponseEntity<List<ProductoResponse>>listar(
            @RequestParam(required = false)String nombre,
            @RequestParam(required = false)String categoria,
            @RequestParam(required = false)BigDecimal precioMin,
            @RequestParam(required = false)BigDecimal precioMax
            ){
        return ResponseEntity.ok(productoService.listar(
                nombre, categoria, precioMin, precioMax));
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener productos por id",
            tags = {"Productos-Consulta"}
    )
    public ResponseEntity<ProductoResponse>listar(
            @PathVariable @Positive(message = "El id debe ser positivo")Long id
    ){
        return ResponseEntity.ok(productoService.ObtenerPorId(id));
    }
    @PostMapping
    @Operation(
            summary = "Registrar Productos",
            tags = {"Productos-Consulta"}
    )
    public ResponseEntity<ProductoResponse>registrar(
            @Valid  @RequestBody ProductoRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar  producto",tags = {"Productos - Gestion"})

    public ResponseEntity <ProductoResponse> actualizar
            (@Valid @RequestBody ProductoRequest request, @PathVariable @Positive(message = "EL valor debe de ser positivo") Long id)
    {
        return  ResponseEntity.ok(productoService.actualizar(request,id));
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar productos por id",
            tags = {"Productos-Consulta"}
    )
    public ResponseEntity<Void>eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo")Long id
    ){
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
