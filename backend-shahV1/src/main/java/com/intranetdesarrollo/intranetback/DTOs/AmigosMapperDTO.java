package com.intranetdesarrollo.intranetback.DTOs;

import com.intranetdesarrollo.intranetback.modelos.Amigos;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AmigosMapperDTO implements Function<Amigos, AmigosDTO> {
    @Override
    public AmigosDTO apply(Amigos amigo){
        return new AmigosDTO(amigo.getJugador().getId(), amigo.getAmigo().getId(), amigo.getAmigo().getUsuario(), amigo.getAmigo().getCorreo());
    }
}
