package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.modelos.Noticias;
import com.intranetdesarrollo.intranetback.servicios.NoticiasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Noticias")
public class NoticiasControlador {

    private final NoticiasService noticiasService;

    public NoticiasControlador(NoticiasService noticiasService) {
        this.noticiasService = noticiasService;
    }

    @GetMapping("/all")
    public List<Noticias> getAllNoticias(){
        return this.noticiasService.getAllNoticias();
    }
}
