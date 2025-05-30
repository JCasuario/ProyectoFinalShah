package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "chats")
@Table(name = "chats")
@Data
public class Chats {

    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "mensaje")
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "id_remitente")
    private Jugadores remitente;

    @ManyToOne
    @JoinColumn(name = "id_receptor")
    private Jugadores receptor;
}
