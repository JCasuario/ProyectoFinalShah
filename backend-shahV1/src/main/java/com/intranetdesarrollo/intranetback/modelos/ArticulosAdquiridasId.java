package com.intranetdesarrollo.intranetback.modelos;

import java.util.Objects;

public class ArticulosAdquiridasId {

    private Integer articulo;
    private Integer jugador;

    //Constructor vacio
    public ArticulosAdquiridasId() {
    }

    public ArticulosAdquiridasId(Integer articulo, Integer jugador) {
        this.articulo = articulo;
        this.jugador = jugador;
    }

    //Hash y equals
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArticulosAdquiridasId that = (ArticulosAdquiridasId) o;
        return Objects.equals(articulo, that.articulo) && Objects.equals(jugador, that.jugador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articulo, jugador);
    }
}
