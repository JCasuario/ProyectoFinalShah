package com.intranetdesarrollo.intranetback.repositorios;

import com.intranetdesarrollo.intranetback.modelos.Articulos;
import com.intranetdesarrollo.intranetback.modelos.ArticulosAdquiridas;
import com.intranetdesarrollo.intranetback.modelos.ArticulosAdquiridasId;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlticulosAdquiridosRepositorio extends JpaRepository<ArticulosAdquiridas, ArticulosAdquiridasId> {
    List<ArticulosAdquiridas> findByJugador(Jugadores jugador);

    boolean existsByJugadorAndArticulo(Jugadores jugador, Articulos articulo);
}
