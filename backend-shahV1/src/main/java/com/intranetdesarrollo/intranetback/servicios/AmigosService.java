package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.DTOs.AmigosDTO;
import com.intranetdesarrollo.intranetback.DTOs.AmigosMapperDTO;
import com.intranetdesarrollo.intranetback.modelos.Amigos;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import com.intranetdesarrollo.intranetback.repositorios.AmigoRepositorio;
import com.intranetdesarrollo.intranetback.repositorios.JugadoresRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmigosService {
    private final AmigoRepositorio amigoRepositorio;
    private final AmigosMapperDTO amigosMapperDTO;
    private final JugadoresRepositorio jugadoresRepositorio;

    public AmigosService(AmigoRepositorio amigoRepositorio, AmigosMapperDTO amigosMapperDTO, JugadoresRepositorio jugadoresRepositorio) {
        this.amigoRepositorio = amigoRepositorio;
        this.amigosMapperDTO = amigosMapperDTO;
        this.jugadoresRepositorio = jugadoresRepositorio;
    }

    public List<AmigosDTO> getAllAmigosByUsuario(String usuario){
        Optional<Jugadores> jugador = this.jugadoresRepositorio.findByUsuario(usuario);
        System.out.println("Hasta aquí llega el usuario");
        System.out.println(jugador);
        return this.amigoRepositorio.findByJugador(jugador).stream().map(amigosMapperDTO).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Boolean> guardarAmigo(Integer idJugador, Integer idAmigo){
        try{
            Jugadores jugador = jugadoresRepositorio.findById(idJugador).orElseThrow();
            Jugadores amigo = jugadoresRepositorio.findById(idAmigo).orElseThrow();
            Amigos relacion = new Amigos(jugador, amigo);

            amigoRepositorio.save(relacion);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
