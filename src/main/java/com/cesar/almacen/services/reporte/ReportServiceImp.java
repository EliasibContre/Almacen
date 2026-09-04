package com.cesar.almacen.services.reporte;

import com.cesar.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.cesar.almacen.enums.EstadoVenta;
import com.cesar.almacen.repositories.ReporteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReportServiceImp implements ReporteService{
    private final ReporteRepository reporteRepository;

    @Override
    public List<ReporteVentasSucursalResponse> obtenerVetnasPorSucursal() {
        log.info("Generando Reporte de ventas por sucursal");
        return reporteRepository.ObtenerVentasPorSucursal(EstadoVenta.REGISTRADA);
    }
}
