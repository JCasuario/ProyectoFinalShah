package com.intranetdesarrollo.intranetback.modelos;

import java.util.Objects;

public class AmigosId {
    private Integer jugador;
    private Integer amigo;

    //Constructor
    public AmigosId() {
    }

    public AmigosId(Integer jugador, Integer amigo) {
        this.jugador = jugador;
        this.amigo = amigo;
    }

    //Hash y equals

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AmigosId amigosId = (AmigosId) o;
        return Objects.equals(jugador, amigosId.jugador) && Objects.equals(amigo, amigosId.amigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jugador, amigo);
    }
}
