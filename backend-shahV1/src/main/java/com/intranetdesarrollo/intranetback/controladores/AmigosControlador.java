package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.DTOs.AmigoRegistroDTO;
import com.intranetdesarrollo.intranetback.DTOs.AmigosDTO;
import com.intranetdesarrollo.intranetback.servicios.AmigosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Amigos")
public class AmigosControlador {
    private final AmigosService amigosService;

    public AmigosControlador(AmigosService amigosService) {
        this.amigosService = amigosService;
    }

    @GetMapping("/Usuario")
    public List<AmigosDTO> getAllAmigosByUsuario(@RequestParam String usuario){
        return this.amigosService.getAllAmigosByUsuario(usuario);
    }

    @PostMapping("/Guardar")
    public ResponseEntity<Boolean> guardarRelacion(@RequestBody AmigoRegistroDTO amigo){
        System.out.println("HOLA LADRONA ERES UNA LADRONA HOLA");
        System.out.println("HOLA LADRONA ERES UNA LADRONA HOLA");
        return this.amigosService.guardarAmigo(amigo.idJugador(),amigo.idAmigo());
    }
}
