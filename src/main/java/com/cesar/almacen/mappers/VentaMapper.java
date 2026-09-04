package com.cesar.almacen.mappers;

import com.cesar.almacen.dto.ventas.DetalleVentaRequest;
import com.cesar.almacen.dto.ventas.DetalleVentaResponse;
import com.cesar.almacen.dto.ventas.VentaRequest;
import com.cesar.almacen.dto.ventas.VentaResponse;
import com.cesar.almacen.entities.DetalleVenta;
import com.cesar.almacen.entities.Producto;
import com.cesar.almacen.entities.Sucursal;
import com.cesar.almacen.entities.Venta;
import com.cesar.almacen.enums.EstadoVenta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class VentaMapper {
    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final SucursalMApper sucursalMApper;

    public Venta requestAEntidad(VentaRequest request, Sucursal sucursal){
        if (request==null)
            return null;
        return Venta.builder()
                .fecha(LocalDate.now())
                .estadoVenta(EstadoVenta.REGISTRADA)
                .sucursal(sucursal)
                .build();
    }
    public DetalleVenta detalleRequesAEntidad(DetalleVentaRequest request, Producto producto){
        if (request==null || producto==null)
            return null;
        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(request.cantidadProducto())
                .precioProducto(producto.getPrecio())
                .build();
    }

    public VentaResponse entidadAResponse(Venta venta){
        if (venta==null)
            return null;
        List<DetalleVentaResponse> detalles=
                venta.getDetalleVentas()
                        .stream()
                        .map(this::detalleEntidadAResponse)
                        .toList();
        BigDecimal total=detalles.stream()
                .map(DetalleVentaResponse::subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new VentaResponse(
                venta.getId(),
                venta.getFecha().format(FORMATO_FECHA),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMApper.entidadAResponse(venta.getSucursal()),
                detalles,
                total

        );
    }

    private DetalleVentaResponse detalleEntidadAResponse(DetalleVenta detalle){
        BigDecimal subtotal= detalle.getPrecioProducto().multiply(BigDecimal.valueOf(detalle.getCantidadProducto()));
        return  new DetalleVentaResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                subtotal
        );
    }
}
