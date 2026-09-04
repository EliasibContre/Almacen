package com.cesar.almacen.services.producto;

import com.cesar.almacen.specifications.ProductoSpecification;
import org.springframework.data.jpa.domain.Specification;
import com.cesar.almacen.dto.productos.ProductoRequest;
import com.cesar.almacen.dto.productos.ProductoResponse;
import com.cesar.almacen.entities.Producto;
import com.cesar.almacen.enums.Categoria;
import com.cesar.almacen.mappers.ProductoMapper;
import com.cesar.almacen.repositories.ProductoRepository;
import com.cesar.almacen.exceptions.RecursoNoEncotradoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService{

    private  final ProductoMapper productoMapper;
    private final ProductoRepository productoRepository;



    @Override
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Buscando productos con nombre: {}, categoria: {}, "+ "precio minimo: {} y precio maximo: {}",nombre,categoria,precioMin,precioMax);
        validarRangoPrecios(precioMin,precioMax);
        Categoria categoriaFiltro= obtenerCategoriaFiltro(categoria);

        Specification<Producto>specification= Specification.allOf(
                ProductoSpecification.nombreContiene(nombre),
                ProductoSpecification.categoriaIgual(categoriaFiltro),
                ProductoSpecification.precioMayorOIgual(precioMin),
                ProductoSpecification.precioMenorOIgual(precioMax)
        );
        return productoRepository.findAll(specification).stream().map(productoMapper::entidadAResponse).toList();
    }

    @Override
    public ProductoResponse ObtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        Producto producto = productoMapper.requestAEntidad(request, Categoria.obtenerCategoriaPorDescripcion(request.categoria()));

        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado ",producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOException(id);

        log.info("Actualizando producto con id {}",id);

        producto.actualizar(request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()),
                request.precio(),
                request.cantidad());

        log.info("Producto con id {} actualizado id ",id);

        return  productoMapper.entidadAResponse(producto);

    }

    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProductoOException(id);

        log.info("Eliminando producto con id {}",id);

        productoRepository.delete(producto);

    }
    private Producto obtenerProductoOException(Long id)
    {
        log.info("Obteniendo producto");

        return  productoRepository.findById(id).orElseThrow(()-> new RecursoNoEncotradoException("Id no encontrado con id "+id));
    }
    private Categoria obtenerCategoriaFiltro(String categoria){
        if (categoria==null || categoria.isBlank())
            return null;
        return Categoria.obtenerCategoriaPorDescripcion(
                categoria.trim()
        );
    }
    private void validarRangoPrecios( BigDecimal precioMin, BigDecimal precioMax){
        validarPrecioNoNegativo(precioMin, "minimo");
        validarPrecioNoNegativo(precioMax,"maximo");
        if (precioMin != null && precioMax != null && precioMin.compareTo(precioMax)>0)
            throw new IllegalArgumentException("El precio minimo no puede ser mayor al precio maximo");

    }
    private void validarPrecioNoNegativo(BigDecimal precio,String nombre){
        if (precio != null && precio.compareTo(BigDecimal.ZERO)<0)
            throw new IllegalArgumentException("El precio" +nombre+ "no puede ser negativo");
    }
}

