package com.intranetdesarrollo.intranetback.excepciones;

import java.util.Date;

public class MensajeError {

    public Date fecha;
    public String mensaje;

    public MensajeError() {}

    public MensajeError(Date fecha, String mensaje) {
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

