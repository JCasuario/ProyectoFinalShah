package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "administradores")
@Table(name = "administradores")
@Data
public class Administradores {

    @Id
    @NotNull
    @Column(name = "correo")
    private String correo;

    @NotNull
    @Column(name = "usuario")
    private String usuario;

    @NotNull
    @Column(name = "constraseña")
    private String contrasena;

}
