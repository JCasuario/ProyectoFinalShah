package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.DTOs.JugadorObtenidoDTO;
import com.intranetdesarrollo.intranetback.servicios.JugadoresService;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Jugadores")
@RestController
public class JugadoresControlador {

    private final JugadoresService jugadoresService;

    public JugadoresControlador(JugadoresService jugadoresService) {
        this.jugadoresService = jugadoresService;
    }

    @GetMapping("/all")
    public List<Jugadores> getAllJugadores(){
        return this.jugadoresService.getAllJugadores();
    }

    @GetMapping("/allOrdenado")
    public List<Jugadores> getAllJugadoresOrdenado(){
        return this.jugadoresService.getAllJugadoresOrdenado();
    }

    @GetMapping("/likeUsuario")
    public List<Jugadores> getJugadoresLikeUsuario(@RequestParam String usuario){
        return this.jugadoresService.getJugadoresLikeUsuario(usuario);
    }

    @GetMapping("/jugadorLogueado")
    public ResponseEntity<JugadorObtenidoDTO> jugadorAuthenticado(){
        Authentication autentificacion = SecurityContextHolder.getContext().getAuthentication();

        Jugadores jugadorLogueado = (Jugadores) autentificacion.getPrincipal();
        JugadorObtenidoDTO devolucion = new JugadorObtenidoDTO(jugadorLogueado.getId(),
                                                               jugadorLogueado.getUsuario(),
                                                               jugadorLogueado.getCorreo(),
                                                               jugadorLogueado.getRanking(),
                                                               jugadorLogueado.getMonedas(),
                                                               jugadorLogueado.getNivel(),
                                                               jugadorLogueado.getImagen());

        return  ResponseEntity.ok(devolucion);
    }

    @PutMapping("/modificar/{usuario}")
    public Jugadores modificarJugador(@PathVariable String usuario, @RequestBody Jugadores jugadorModificado){
        return this.jugadoresService.modificarJugador(usuario, jugadorModificado);
    }

    @PutMapping("/modificarRanking")
    public Jugadores modificarRanking(@RequestParam Integer id, @RequestParam Integer modificacion){
        return this.jugadoresService.modificarRanking(id, modificacion);
    }

    @PutMapping("/modificarMoneda")
    public Jugadores modificarMoneda(@RequestParam Integer id, @RequestParam Integer modificacion){
        return this.jugadoresService.modificarMoneda(id, modificacion);
    }

    @PutMapping("/modificarPerfilImagen")
    public Jugadores modificarImagenPerfil(@RequestParam Integer id, @RequestParam String imagenNueva){
        return this.jugadoresService.modificarImagenPerfil(id, imagenNueva);
    }

    @PutMapping("/modificarJugadorId")
    public ResponseEntity<?> modificarJugadorId(@RequestParam Integer id, @RequestBody Jugadores jugadorModificado) {
        try {
            Jugadores actualizado = jugadoresService.modificarJugadorId(id, jugadorModificado);
            return ResponseEntity.ok(actualizado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Correo o usuario ya registrados en la base de datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al modificar jugador: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarJugador(@PathVariable Integer id){
        return this.jugadoresService.eliminarJugador(id);
    }
}
