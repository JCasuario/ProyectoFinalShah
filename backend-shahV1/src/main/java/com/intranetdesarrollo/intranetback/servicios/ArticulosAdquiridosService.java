package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.DTOs.ArticulosAdquiridosDTO;
import com.intranetdesarrollo.intranetback.DTOs.ArticulosAdquiridosMapperDTO;
import com.intranetdesarrollo.intranetback.modelos.Articulos;
import com.intranetdesarrollo.intranetback.modelos.ArticulosAdquiridas;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import com.intranetdesarrollo.intranetback.repositorios.AlticulosAdquiridosRepositorio;
import com.intranetdesarrollo.intranetback.repositorios.ArticulosRepositorio;
import com.intranetdesarrollo.intranetback.repositorios.JugadoresRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticulosAdquiridosService {

    private final AlticulosAdquiridosRepositorio alticulosAdquiridosRepositorio;
    private final JugadoresRepositorio jugadoresRepositorio;
    private final ArticulosAdquiridosMapperDTO articulosAdquiridosMapperDTO;
    private final ArticulosRepositorio articulosRepositorio;

    public ArticulosAdquiridosService(AlticulosAdquiridosRepositorio alticulosAdquiridosRepositorio, JugadoresRepositorio jugadoresRepositorio, ArticulosAdquiridosMapperDTO articulosAdquiridosMapperDTO, ArticulosRepositorio articulosRepositorio) {
        this.alticulosAdquiridosRepositorio = alticulosAdquiridosRepositorio;
        this.jugadoresRepositorio = jugadoresRepositorio;
        this.articulosAdquiridosMapperDTO = articulosAdquiridosMapperDTO;
        this.articulosRepositorio = articulosRepositorio;
    }

    public List<ArticulosAdquiridosDTO> obtenerArticulosJugador(Integer id){
        Jugadores jugadores = this.jugadoresRepositorio.findById(id).orElseThrow();
        return this.alticulosAdquiridosRepositorio.findByJugador(jugadores).stream().map(articulosAdquiridosMapperDTO).collect(Collectors.toList());
    }

    public ArticulosAdquiridas comprarArticulo(Integer idArticulo, Integer idJugador){
        Jugadores jugador = this.jugadoresRepositorio.findById(idJugador).orElseThrow();
        Articulos articulo = this.articulosRepositorio.findById(idArticulo).orElseThrow();
        Date fecha = new Date();

        // Verificar si ya lo tiene
        boolean yaAdquirido = this.alticulosAdquiridosRepositorio.existsByJugadorAndArticulo(jugador, articulo);
        if (yaAdquirido) {
            throw new IllegalStateException("El jugador ya ha adquirido este artículo.");
        }

        ArticulosAdquiridas compra = new ArticulosAdquiridas(articulo, jugador, fecha);
        return this.alticulosAdquiridosRepositorio.save(compra);
    }
}
