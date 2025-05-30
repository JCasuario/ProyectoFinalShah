package com.intranetdesarrollo.intranetback.repositorios;

import com.intranetdesarrollo.intranetback.modelos.Articulos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticulosRepositorio extends JpaRepository<Articulos, Integer> {
    @Override
    List<Articulos> findAll();
}
