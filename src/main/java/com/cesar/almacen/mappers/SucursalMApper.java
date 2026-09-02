package com.cesar.almacen.mappers;

import com.cesar.almacen.dto.sucursales.SucursalRequest;
import com.cesar.almacen.dto.sucursales.SucursalResponse;
import com.cesar.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMApper {
    public Sucursal requestaAEntidad(SucursalRequest request){
        if (request == null)return null;
        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion().trim())
                .build();
    }
    public SucursalResponse entidadAResponse(Sucursal sucursal){
        if (sucursal == null)return null;
        return new SucursalResponse(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion()
        );
    }
}
