package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.modelos.Noticias;
import com.intranetdesarrollo.intranetback.repositorios.NoticiasRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NoticiasService {

    private final NoticiasRepositorio noticiasRepositorio;

    public NoticiasService(NoticiasRepositorio noticiasRepositorio) {
        this.noticiasRepositorio = noticiasRepositorio;
    }

    public List<Noticias> getAllNoticias(){
        return this.noticiasRepositorio.findAll();
    }
}
