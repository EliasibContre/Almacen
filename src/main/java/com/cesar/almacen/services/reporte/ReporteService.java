package com.cesar.almacen.services.reporte;

import com.cesar.almacen.dto.reportes.ReporteVentasSucursalResponse;

import java.util.List;

public interface ReporteService {
    List<ReporteVentasSucursalResponse>obtenerVetnasPorSucursal();
}

