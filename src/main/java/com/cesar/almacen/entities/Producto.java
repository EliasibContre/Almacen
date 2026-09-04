package com.cesar.almacen.entities;

import com.cesar.almacen.enums.Categoria;
import com.cesar.almacen.utils.StringCustomUtils;
import com.cesar.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID_PRODUCTO")
    private Long id;

    @Column (name = "NOMBRE",length = 30,nullable = false)
    private String nombre;

    @Column(name = "CATEGORIA",nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO",nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void aumentarCantidad(Integer cantidad){
        ValoresNumericosUtils.validarEnteroPositivo(cantidad,"la cantidad debe de ser positiva");
        this.cantidad+=cantidad;
    }
    public void descontarCantidad(Integer cantidad){
        ValoresNumericosUtils.validarEnteroPositivo(cantidad,"la cantidad debe de ser positiva");
        if (cantidad>this.cantidad)
            throw new IllegalArgumentException("Stock insuficiente para el producto"+nombre+
                    ". Disponible"+this.cantidad+",solicitado"+cantidad);
        this.cantidad-=cantidad;
    }

    public void validarDAtos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad){
        StringCustomUtils.validarTamanio(nombre,5,
                30,"datos erroneos");
        if (categoria==null)
            throw new IllegalArgumentException("la categoria es requerida");

        ValoresNumericosUtils.validarBigDecimalPositivo(precio,"El precio es requerido y debe ser positivo");
        ValoresNumericosUtils.validarEnteroPositivo(cantidad,"La cantidad es requerida y debe de ser positiva");
    }

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad){
        validarDAtos(nombre, categoria, precio, cantidad);
        this.nombre=nombre.trim();
        this.categoria=categoria;
        this.precio=precio;
        this.cantidad=cantidad;
    }
}
