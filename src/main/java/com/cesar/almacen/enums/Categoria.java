package com.cesar.almacen.enums;

import com.cesar.almacen.exceptions.RecursoNoEncotradoException;
import com.cesar.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICOS("Electrónica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "la descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (Categoria categoria : values()){
            if(StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return categoria;
        }
        throw new RecursoNoEncotradoException("NO existe una categoria" + descripcion);
    }

}
