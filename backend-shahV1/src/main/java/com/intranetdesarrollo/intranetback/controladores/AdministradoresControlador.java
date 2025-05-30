package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.modelos.Administradores;
import com.intranetdesarrollo.intranetback.servicios.AdministradoresService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Administradores")
public class AdministradoresControlador {

    private final AdministradoresService administradoresService;

    public AdministradoresControlador(AdministradoresService administradoresService) {
        this.administradoresService = administradoresService;
    }

    @GetMapping("/all")
    public List<Administradores> getAllAdministradores(){
        return this.administradoresService.getAllAdministradores();
    }
}
