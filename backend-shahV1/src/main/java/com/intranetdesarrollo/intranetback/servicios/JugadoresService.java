package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import com.intranetdesarrollo.intranetback.repositorios.JugadoresRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class JugadoresService {
    private final JugadoresRepositorio jugadoresRepositorio;

    public JugadoresService(JugadoresRepositorio jugadoresRepositorio) {
        this.jugadoresRepositorio = jugadoresRepositorio;
    }

    public List<Jugadores> getAllJugadores(){

        return this.jugadoresRepositorio.findAll();
    }

    public List<Jugadores> getAllJugadoresOrdenado(){

        return this.jugadoresRepositorio.findAllByOrderByRankingDesc();
    }

    public List<Jugadores> getJugadoresLikeUsuario(String usuario){
        return this.jugadoresRepositorio.findByUsuarioLike(usuario);
    }

    public Jugadores modificarJugador(String usuario, Jugadores jugadorModificado){
        Jugadores jugadorOriginal = this.jugadoresRepositorio.findByUsuario(usuario).orElseThrow();
        jugadorOriginal.setCorreo(jugadorModificado.getCorreo());
        jugadorOriginal.setUsuario(jugadorModificado.getUsuario());
        jugadorOriginal.setMonedas(jugadorModificado.getMonedas());
        jugadorOriginal.setRanking(jugadorModificado.getRanking());
        jugadorOriginal.setNivel(jugadorModificado.getNivel());


        return this.jugadoresRepositorio.save(jugadorOriginal);
    }

    public Jugadores modificarJugadorId(Integer id, Jugadores jugadorModificado){
        Jugadores jugadorOriginal = this.jugadoresRepositorio.findById(id).orElseThrow();
        jugadorOriginal.setCorreo(jugadorModificado.getCorreo());
        jugadorOriginal.setUsuario(jugadorModificado.getUsuario());
        jugadorOriginal.setMonedas(jugadorModificado.getMonedas());
        jugadorOriginal.setRanking(jugadorModificado.getRanking());
        jugadorOriginal.setNivel(jugadorModificado.getNivel());
        jugadorOriginal.setImagen(jugadorModificado.getImagen());


        return this.jugadoresRepositorio.save(jugadorOriginal);
    }

    public Jugadores modificarRanking(Integer id, Integer modificacion){
        Jugadores jugadorOriginal = this.jugadoresRepositorio.findById(id).orElseThrow();
        jugadorOriginal.setRanking(jugadorOriginal.getRanking() + modificacion);
        jugadorOriginal.setMonedas(jugadorOriginal.getMonedas() + modificacion);

        return this.jugadoresRepositorio.save(jugadorOriginal);
    }

    public Jugadores modificarMoneda(Integer id, Integer modificacion){
        Jugadores jugadorOriginal = this.jugadoresRepositorio.findById(id).orElseThrow();
        jugadorOriginal.setMonedas(jugadorOriginal.getMonedas() + modificacion);

        return this.jugadoresRepositorio.save(jugadorOriginal);
    }

    public Jugadores guardarJugador(Jugadores jugador){
        return this.jugadoresRepositorio.save(jugador);
    }

    public Jugadores modificarImagenPerfil(Integer id, String imagenNueva){
        Jugadores jugador = this.jugadoresRepositorio.findById(id).orElseThrow();
        jugador.setImagen(imagenNueva);
        return this.jugadoresRepositorio.save(jugador);
    }

    public ResponseEntity<String> eliminarJugador(Integer id) {
        if (!jugadoresRepositorio.existsById(id)) {
            return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            jugadoresRepositorio.deleteById(id);
            return new ResponseEntity<>("Jugador eliminado correctamente", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("No se puede eliminar el jugador porque tiene datos relacionados (por ejemplo, partidas o estadísticas).", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado al eliminar jugador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
