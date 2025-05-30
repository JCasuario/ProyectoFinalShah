package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "noticias")
@Table(name = "noticias")
@Data
public class Noticias {

    @Id
    @NotNull
    @Column(name = "id_noticia")
    private Integer idNoticia;

    @NotNull
    @Column(name = "noticia")
    private String noticia;

    @NotNull
    @Column(name = "imagen_noticia")
    private String imagenNoticia;

    @NotNull
    @Column(name = "titulo")
    private String titulo;
}
