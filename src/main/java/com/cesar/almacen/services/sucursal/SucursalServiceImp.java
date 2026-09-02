package com.cesar.almacen.services.sucursal;

import com.cesar.almacen.dto.sucursales.SucursalRequest;
import com.cesar.almacen.dto.sucursales.SucursalResponse;
import com.cesar.almacen.entities.Sucursal;
import com.cesar.almacen.exceptions.RecursoNoEncotradoException;
import com.cesar.almacen.mappers.SucursalMApper;
import com.cesar.almacen.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImp implements SucursalService{

    private final SucursalRepository sucursalRepository;
    private final SucursalMApper sucursalMApper;

    @Override
    public List<SucursalResponse> listar() {
        return sucursalRepository.findAll().stream()
                .map(sucursalMApper::entidadAResponse).toList();
    }

    @Override
    public SucursalResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando nueva sucursal");
        Sucursal sucursal=sucursalMApper.requestaAEntidad(request);
        validarDatosUnicos(request);
        sucursalRepository.save(sucursal);
        log.info("nueva sucursal: {}",sucursal.getNombre());
        return sucursalMApper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {
        Sucursal sucursal=obtenerSucursalException(id);
        log.info("Actualizando sucursal con id:{}",id);
        validarCambiossUnicos(request,id);
        sucursal.actualizar(request.nombre(),request.direccion());
        log.info("Sucursal con i {} actualizada",id);
        return sucursalMApper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal=obtenerSucursalException(id);
        log.info("eliminando sucursal con id:{}",id);
        sucursalRepository.delete(sucursal);
        log.info("sucursal con id:{} eliminada",id);

    }
    private Sucursal obtenerSucursalException(Long id){
        return sucursalRepository.findById(id).orElseThrow(()->new RecursoNoEncotradoException("No se encontro sucursal"));

    }

    private void validarDatosUnicos(SucursalRequest request){
        log.info("validando nombre unico....");
        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre :"+request.nombre());
    }
    private void validarCambiossUnicos(SucursalRequest request, Long id){
        log.info("validando cambio nombre unico....");
        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(),id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre :"+request.nombre());
    }
}


