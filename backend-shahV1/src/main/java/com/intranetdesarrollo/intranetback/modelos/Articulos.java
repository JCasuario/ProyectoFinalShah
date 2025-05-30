package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Entity(name = "articulos")
@Table(name = "articulos")
@Data
public class Articulos {

    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "tipo", length = 7)
    private String tipo;

    @NotNull
    @Column(name = "nombre", length = 50)
    private String nombre;

    @NotNull
    @Column(name = "precio")
    private int precio;

    @NotNull
    @Column(name = "disponibilidad")
    private boolean disponibilidad;

    @Column(name = "Imagen")
    private String imagen;

}
