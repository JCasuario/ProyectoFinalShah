package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "partidas")
@Table(name = "partidas")
@Data
public class Partidas {

    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_jugador1")
    private Jugadores jugador1;

    @ManyToOne
    @JoinColumn(name = "id_jugador2")
    private Jugadores jugador2;
}
