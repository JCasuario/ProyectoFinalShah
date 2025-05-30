package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.modelos.Administradores;
import com.intranetdesarrollo.intranetback.repositorios.AdministradoresRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdministradoresService {

    private final AdministradoresRepositorio administradoresRepositorio;

    public AdministradoresService(AdministradoresRepositorio administradoresRepositorio) {
        this.administradoresRepositorio = administradoresRepositorio;
    }

    public List<Administradores> getAllAdministradores(){
        return this.administradoresRepositorio.findAll();
    }
}
