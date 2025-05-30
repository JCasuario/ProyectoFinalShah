package com.intranetdesarrollo.intranetback.DTOs;

import java.util.Objects;

public record AmigosDTO(
    Integer id_jugador,
    Integer id_amigo,
    String usuario_amigo,
    String correo_amigo
) {
    public AmigosDTO {
    }

    @Override
    public Integer id_jugador() {
        return id_jugador;
    }

    @Override
    public Integer id_amigo() {
        return id_amigo;
    }

    @Override
    public String usuario_amigo() {
        return usuario_amigo;
    }

    @Override
    public String correo_amigo() {
        return correo_amigo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AmigosDTO amigosDTO = (AmigosDTO) o;
        return Objects.equals(id_amigo, amigosDTO.id_amigo) && Objects.equals(id_jugador, amigosDTO.id_jugador) && Objects.equals(usuario_amigo, amigosDTO.usuario_amigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_jugador, id_amigo, usuario_amigo);
    }
}
