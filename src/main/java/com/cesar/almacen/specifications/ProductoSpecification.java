package com.cesar.almacen.specifications;

import com.cesar.almacen.entities.Producto;
import com.cesar.almacen.enums.Categoria;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Locale;

//no se va a extender mediante herencia
public final class ProductoSpecification {
    //evitamos o  no necesitamos new **Objeto**()
    private ProductoSpecification(){
    }
    public static Specification<Producto> nombreContiene(String nombre){
        if (nombre==null || nombre.isBlank())
            return Specification.unrestricted();
        String nombreNormalizado=nombre.trim().toLowerCase(Locale.ROOT);
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like( criteriaBuilder.lower(root.get("nombre")),
                "%" + nombreNormalizado + "%"));
    }
    public static Specification<Producto> categoriaIgual(Categoria categoria){
        if (categoria==null)
            return Specification.unrestricted();
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoria"),
                        categoria));
    }
    public static Specification<Producto> precioMayorOIgual(BigDecimal precioMin){
        if (precioMin==null)
            return Specification.unrestricted();
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("precio"),
                        precioMin));
    }
    public static Specification<Producto> precioMenorOIgual(BigDecimal precioMax){
        if (precioMax==null)
            return Specification.unrestricted();
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("precio"),
                        precioMax));
    }
}
