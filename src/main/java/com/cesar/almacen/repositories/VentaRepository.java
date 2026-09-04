package com.cesar.almacen.repositories;

import com.cesar.almacen.entities.Venta;
import com.cesar.almacen.enums.EstadoVenta;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Long> {
    @EntityGraph(attributePaths = {
            "sucursal",
            "detalleVentas",
            "detalleVentas.producto"
    })
    //nos mostrara las ventas hechas
    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);
    @EntityGraph(attributePaths = {
            "sucursal",
            "detalleVentas",
            "detalleVentas.producto"
    })
    //hay ventas activas?-osea consulta
    Optional<Venta> findByIdAndEstadoVenta(Long id, EstadoVenta estadoVenta);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM Venta v WHERE v.id = :id")
    //nos cancela sin cargar el arbol
    Optional<Venta> buscarPorIdConBloqueo(@Param("id")Long id);
}
