package com.cesar.almacen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

}
