package com.intranetdesarrollo.intranetback.DTOs;

public record JugadorObtenidoDTO(
        Integer id,
        String usuario,
        String correo,
        Integer ranking,
        Integer monedas,
        Integer nivel,
        String imagen
) {
}
