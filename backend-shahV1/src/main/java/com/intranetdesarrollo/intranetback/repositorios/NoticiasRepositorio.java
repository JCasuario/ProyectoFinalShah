package com.intranetdesarrollo.intranetback.repositorios;

import com.intranetdesarrollo.intranetback.modelos.Noticias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiasRepositorio extends JpaRepository<Noticias, Integer> {
}
