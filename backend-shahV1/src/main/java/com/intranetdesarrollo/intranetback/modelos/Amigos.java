package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "amigos")
@Table(name = "amigos")
@IdClass(AmigosId.class)
@Data
public class Amigos {
    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_jugador", referencedColumnName = "id")
    Jugadores jugador;

    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_amigo", referencedColumnName = "id")
    Jugadores amigo;

    public Amigos() {
    }

    public Amigos(Jugadores jugador, Jugadores amigo) {
        this.jugador = jugador;
        this.amigo = amigo;
    }
}