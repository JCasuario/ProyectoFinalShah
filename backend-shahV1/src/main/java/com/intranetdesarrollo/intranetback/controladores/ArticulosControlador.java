package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.modelos.Articulos;
import com.intranetdesarrollo.intranetback.servicios.ArticuloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Articulos")
public class ArticulosControlador {

    private final ArticuloService articuloService;

    public ArticulosControlador(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping("/all")
    public List<Articulos> getAllArticulos(){
        return this.articuloService.getAllArticulos();
    }
}
