package com.cesar.almacen.services.venta;

import com.cesar.almacen.dto.ventas.VentaRequest;
import com.cesar.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse>listar();
    VentaResponse obtenerPorIdActiva(Long id);
    VentaResponse registrar(VentaRequest request);
    VentaResponse cancelar (Long id);
}
