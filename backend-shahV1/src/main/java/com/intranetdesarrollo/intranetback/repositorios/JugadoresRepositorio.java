package com.intranetdesarrollo.intranetback.repositorios;

import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JugadoresRepositorio extends JpaRepository<Jugadores, Integer> {

    Optional<Jugadores> findByUsuario(String usuario);

    Optional<Jugadores> findById(Integer id);

    List<Jugadores> findAllByOrderByRankingDesc();

    List<Jugadores> findByUsuarioLike(String usuario);
}
