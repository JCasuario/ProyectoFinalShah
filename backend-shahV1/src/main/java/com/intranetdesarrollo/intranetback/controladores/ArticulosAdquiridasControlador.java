package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.DTOs.ArticulosAdquiridosDTO;
import com.intranetdesarrollo.intranetback.modelos.ArticulosAdquiridas;
import com.intranetdesarrollo.intranetback.servicios.ArticulosAdquiridosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Adquiridos")
public class ArticulosAdquiridasControlador {
    private final ArticulosAdquiridosService articulosAdquiridosService;

    public ArticulosAdquiridasControlador(ArticulosAdquiridosService articulosAdquiridosService) {
        this.articulosAdquiridosService = articulosAdquiridosService;
    }

    @GetMapping("/{id}")
    public List<ArticulosAdquiridosDTO> obtenerArticulosJugador(@PathVariable Integer id){
        return this.articulosAdquiridosService.obtenerArticulosJugador(id);
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> comprarArticulos(@RequestParam Integer idArticulo, @RequestParam Integer idJugador){
        try {
            ArticulosAdquiridas compra = this.articulosAdquiridosService.comprarArticulo(idArticulo, idJugador);
            return ResponseEntity.ok(compra);
        } catch (IllegalStateException e) {
            // Aquí capturas la excepción custom y devuelves un error controlado (ejemplo 400 Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Captura cualquier otro error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno"));
        }
    }
}
