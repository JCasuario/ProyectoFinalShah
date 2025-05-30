package com.intranetdesarrollo.intranetback.DTOs;

public class JugadorLogDTO {

    private String usuario;
    private String contrasena;

    //Constructores
    public JugadorLogDTO(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public JugadorLogDTO() {
    }

    //Setters y getters
    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
