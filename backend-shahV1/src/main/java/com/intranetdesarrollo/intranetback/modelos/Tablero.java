package com.intranetdesarrollo.intranetback.modelos;

public class Tablero {
    private String nombreEnvio;
    private String receiverId;
    private String from;
    private String to;
    private String pieza;
    private String tipo;

    public Tablero(String nombreEnvio, String receiverId, String from, String to, String pieza, String tipo) {
        this.nombreEnvio = nombreEnvio;
        this.receiverId = receiverId;
        this.from = from;
        this.to = to;
        this.pieza = pieza;
        this.tipo = tipo;
    }

    public Tablero() {
    }

    public String getNombreEnvio() {
        return nombreEnvio;
    }

    public void setNombreEnvio(String nombreEnvio) {
        this.nombreEnvio = nombreEnvio;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPieza() {
        return pieza;
    }

    public void setPieza(String pieza) {
        this.pieza = pieza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
