package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Table(name = "movimientos")
@Entity(name = "movimientos")
@Data
public class Movimientos {

    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_partida", referencedColumnName = "id")
    private Partidas idPartida;

    @ManyToOne
    @JoinColumn(name = "id_jugador", referencedColumnName = "id")
    private Jugadores idJugador;

    @NotNull
    @Column(name = "turno")
    private Integer turno;

    @NotNull
    @Column(name = "origen")
    private String origen;

    @NotNull
    @Column(name = "destino")
    private String destino;
}
