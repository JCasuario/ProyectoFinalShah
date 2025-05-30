package com.intranetdesarrollo.intranetback.DTOs;

import com.intranetdesarrollo.intranetback.modelos.ArticulosAdquiridas;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArticulosAdquiridosMapperDTO implements Function<ArticulosAdquiridas, ArticulosAdquiridosDTO> {
    @Override
    public ArticulosAdquiridosDTO apply(ArticulosAdquiridas recibido) {
        return new ArticulosAdquiridosDTO(recibido.getArticulo().getId(),
                                          recibido.getJugador().getId(),
                                          recibido.getArticulo().getImagen(),
                                          recibido.getArticulo().getTipo());
    }
}
