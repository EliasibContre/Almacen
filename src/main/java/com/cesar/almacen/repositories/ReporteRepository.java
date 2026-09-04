package com.cesar.almacen.repositories;

import com.cesar.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.cesar.almacen.entities.Venta;
import com.cesar.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReporteRepository extends Repository<Venta, Long> {

    @Query("SELECT new com.cesar.almacen.dto.reportes.ReporteVentasSucursalResponse(" +
            "s.id, " +
            "s.nombre, " +
            "SUM(d.precioProducto * d.cantidadProducto), " +
            "SUM(d.cantidadProducto))" +
            "FROM Venta v " +
            "JOIN v.sucursal s " +
            "JOIN v.detalleVentas d " +
            "WHERE v.estadoVenta = :estado " +
            "GROUP BY s.id, s.nombre " +
            "ORDER BY s.id")
    List<ReporteVentasSucursalResponse>ObtenerVentasPorSucursal(@Param("estado")EstadoVenta estado);
}
