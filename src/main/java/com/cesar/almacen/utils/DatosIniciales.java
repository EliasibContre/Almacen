package com.cesar.almacen.utils;

import com.cesar.almacen.entities.Producto;
import com.cesar.almacen.entities.Sucursal;
import com.cesar.almacen.enums.Categoria;
import com.cesar.almacen.repositories.ProductoRepository;
import com.cesar.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class DatosIniciales implements CommandLineRunner {
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    @Override
    public void run(String... args)throws Exception{
        if (productoRepository.count()==0){
            productoRepository.saveAll(List.of(
                    new Producto(null,
                            "laptop GAmer",
                            Categoria.ELECTRONICOS,
                            BigDecimal.valueOf(25),
                            50),
                    new Producto(null,
                            "Mouse Inalambrico",
                            Categoria.ELECTRONICOS,
                            BigDecimal.valueOf(25),
                            50),
                    new Producto(null,
                            "Camiseta Deportiva",
                            Categoria.ROPA,
                            BigDecimal.valueOf(20),
                            100)
            ));
            log.info("Productos de proueba cargados correctamente");
        }
        if (sucursalRepository.count()==0){
            sucursalRepository.saveAll(List.of(
                    new Sucursal(null,
                            "Sucursal sur","Ca. Enrique Segoviano"),
                    new Sucursal(null,
                            "Sucursal norte","Av. Manuel Guerrero")

            ));
            log.info("Sucursales de proueba cargados correctamente");
        }
    }
}
