package com.intranetdesarrollo.intranetback.repositorios;

import com.intranetdesarrollo.intranetback.modelos.Amigos;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmigoRepositorio extends JpaRepository<Amigos, Jugadores> {
    List<Amigos> findByJugador(Optional<Jugadores> jugadores);
}
