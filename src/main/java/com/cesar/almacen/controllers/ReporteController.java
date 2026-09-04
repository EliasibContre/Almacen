package com.cesar.almacen.controllers;


import com.cesar.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.cesar.almacen.services.reporte.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@AllArgsConstructor
@Tag(name = "Reportes", description = "Enpoint para reportes economiccos")
public class ReporteController {
    private final ReporteService reporteService;
    @GetMapping("/ventas-sucursal")
    @Operation(summary = "obtener reporte economico por sucursal")
    public ResponseEntity<List<ReporteVentasSucursalResponse>>obtenerVentasPorSucursal(){
        return ResponseEntity.ok(reporteService.obtenerVetnasPorSucursal());
    }

}
