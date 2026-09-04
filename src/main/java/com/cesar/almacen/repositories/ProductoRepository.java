package com.cesar.almacen.repositories;

import com.cesar.almacen.entities.Producto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    /*conceptualmente haria esto: SELECT * FROM PRODUCTOS WHERE =? FOR UPDATE-- hacia productos.
    * habra bloqueo hasta que haya un commit o rollback*/
    @Query("SELECT p FROM Producto p WHERE p.id = :id ")
    Optional<Producto> buscarPorIdConBloqueo(@Param("id") Long id);
}
