package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "articulos_adquiridas")
@Table(name = "articulos_adquiridas")
@IdClass(ArticulosAdquiridasId.class)
@Data
public class ArticulosAdquiridas {
    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id")
    Articulos articulo;

    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_jugador", referencedColumnName = "id")
    Jugadores jugador;

    @Column(name = "fecha_compra")
    Date fecha;

    public ArticulosAdquiridas(Articulos articulo, Jugadores jugador, Date fecha) {
        this.articulo = articulo;
        this.jugador = jugador;
        this.fecha = fecha;
    }

    public ArticulosAdquiridas(Articulos articulo, Jugadores jugador) {
        this.articulo = articulo;
        this.jugador = jugador;
    }

    public ArticulosAdquiridas() {
    }
}
