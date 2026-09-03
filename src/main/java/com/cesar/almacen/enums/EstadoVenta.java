package com.cesar.almacen.enums;

import com.cesar.almacen.exceptions.RecursoNoEncotradoException;
import com.cesar.almacen.utils.StringCustomUtils;
import com.cesar.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA(1L,"REGISTRADA"),
    CANCELADA(0L,"Cancelada");
    private final Long codigo;
    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "la descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (EstadoVenta estadoVenta : values()){
            if(StringCustomUtils.quitarAcentos(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }
        throw new RecursoNoEncotradoException("NO existe una categoria" + descripcion);
    }
    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        ValoresNumericosUtils.validadNumeroRequerido(codigo);
        for (EstadoVenta estadoVenta : values()){
            if(estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }
        throw new RecursoNoEncotradoException("NO existe un estado de venta con el codigo: " + codigo);
    }
}
