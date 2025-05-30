package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.modelos.Articulos;
import com.intranetdesarrollo.intranetback.repositorios.ArticulosRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArticuloService {

    private final ArticulosRepositorio articulosRepositorio;

    public ArticuloService(ArticulosRepositorio articulosRepositorio) {
        this.articulosRepositorio = articulosRepositorio;
    }

    public List<Articulos> getAllArticulos(){
        return this.articulosRepositorio.findAll();
    }
}
