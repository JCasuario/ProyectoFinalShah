package com.intranetdesarrollo.intranetback.DTOs;

public record ArticulosAdquiridosDTO(
        Integer idArticulo,
        Integer idUsuario,
        String imagenArticulo,
        String tipo
) {
}
