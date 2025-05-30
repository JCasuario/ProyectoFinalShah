package com.intranetdesarrollo.intranetback.repositorios;

import com.intranetdesarrollo.intranetback.modelos.Administradores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradoresRepositorio extends JpaRepository<Administradores, Integer> {
    Administradores findByUsuario(String usuario);
}
