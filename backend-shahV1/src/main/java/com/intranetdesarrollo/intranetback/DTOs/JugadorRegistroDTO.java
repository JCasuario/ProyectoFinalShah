package com.intranetdesarrollo.intranetback.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public record JugadorRegistroDTO(
        String correo,

        String usuario,

        String contrasena) {

    @Override
    public String correo() {
        return correo;
    }

    @Override
    public String usuario() {
        return usuario;
    }

    @Override
    public String contrasena() {
        return contrasena;
    }
}
