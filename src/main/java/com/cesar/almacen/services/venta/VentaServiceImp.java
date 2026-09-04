package com.cesar.almacen.services.venta;

import com.cesar.almacen.dto.ventas.DetalleVentaRequest;
import com.cesar.almacen.dto.ventas.VentaRequest;
import com.cesar.almacen.dto.ventas.VentaResponse;
import com.cesar.almacen.entities.DetalleVenta;
import com.cesar.almacen.entities.Producto;
import com.cesar.almacen.entities.Sucursal;
import com.cesar.almacen.entities.Venta;
import com.cesar.almacen.enums.EstadoVenta;
import com.cesar.almacen.exceptions.RecursoNoEncotradoException;
import com.cesar.almacen.mappers.VentaMapper;
import com.cesar.almacen.repositories.ProductoRepository;
import com.cesar.almacen.repositories.SucursalRepository;
import com.cesar.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImp implements VentaService{
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final VentaMapper ventaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar(){
        log.info("lisntando ventas registradas");
        return  ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> ListarCanceladas() {
        log.info("listando ventas canceladas");
        return ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorIdActiva(Long id) {
        log.info("Buscando venta registrada con id {}",id);
        Venta venta =ventaRepository.findByIdAndEstadoVenta(id, EstadoVenta.REGISTRADA)
                .orElseThrow(()-> new RecursoNoEncotradoException("No se encontro venta registrada"+"con id"+id));
        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando venta para la sucursal {}",request.idSucursal());
        Sucursal sucursal =  obtenerSucursal(request.idSucursal());
        Venta venta = ventaMapper.requestAEntidad(request, sucursal);
        List<DetalleVentaRequest> detallesOrdenados= request.productos()
                .stream()
                .sorted(Comparator.comparing(DetalleVentaRequest::idProducto))
                .toList();
        for (DetalleVentaRequest detalleRequest : detallesOrdenados){
            Producto producto = obtenerProductoConBloqueo(detalleRequest.idProducto());
            DetalleVenta detalle= ventaMapper.detalleRequesAEntidad(detalleRequest,producto);
            venta.agregarDetalle(detalle);
        }
        Venta ventaGuardada=ventaRepository.save(venta);
        log.info("Venta {} registrada correctamente", ventaGuardada.getId());
        return ventaMapper.entidadAResponse(ventaGuardada);
    }

    @Override
    public VentaResponse cancelar(Long id) {
        log.info("Cancelando venta{}",id);
        Venta venta=ventaRepository
                .buscarPorIdConBloqueo(id)
                .orElseThrow(()->new RecursoNoEncotradoException("No se encontro la venta con id"+id));
        venta.cancelar();
        List<DetalleVenta> detallesOrdenados= venta.getDetalleVentas()
                .stream()
                .sorted(Comparator.comparing(detalle->detalle.getProducto().getId()))
                .toList();
        for (DetalleVenta detalle:detallesOrdenados){
            Producto producto=obtenerProductoConBloqueo(detalle.getProducto().getId());
            producto.aumentarCantidad(detalle.getCantidadProducto());
        }
        log.info("Venta {} venta cancelada correctamente",id);
        return ventaMapper.entidadAResponse(venta);
    }

    private Sucursal obtenerSucursal(Long id){
        return sucursalRepository.findById(id)
                .orElseThrow(()->new RecursoNoEncotradoException("No se encontro sucursal con id"+id));
    }

    //metodos privados
    private Producto obtenerProductoConBloqueo(Long id){
        return productoRepository.buscarPorIdConBloqueo(id).orElseThrow(()->
                new RecursoNoEncotradoException("No se encontro el producto con id"+id));
    }
    private void validarProductosNoDuplicados(List<DetalleVentaRequest> detalles){
        Set<Long> identificadores = new HashSet<>();
        boolean existeDuplicado = detalles.stream()
                .map(DetalleVentaRequest::idProducto)
                .anyMatch(id->!identificadores.add(id));
        if (existeDuplicado)
            throw new IllegalArgumentException("Una venta no puede contener" + "Productos duplicados");
    }


}
