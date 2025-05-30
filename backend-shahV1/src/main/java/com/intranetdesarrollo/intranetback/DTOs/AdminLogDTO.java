package com.intranetdesarrollo.intranetback.DTOs;

public record AdminLogDTO(
        String usuario,
        String contrasena
) {
    @Override
    public String usuario() {
        return usuario;
    }

    @Override
    public String contrasena() {
        return contrasena;
    }
}
