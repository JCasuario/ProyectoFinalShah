package com.intranetdesarrollo.intranetback.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "jugadores")
@Table(name = "jugadores")
public class Jugadores implements UserDetails, Principal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @NotNull
    @Column(name = "correo", length = 254, unique = true)
    private String correo;

    @NotNull
    @Column(name = "usuario", length = 30, unique = true)
    private String usuario;

    @NotNull
    @Column(name = "contraseña", length = 150)
    private String contrasena;

    @Column(name = "ranking")
    private int ranking;

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "monedas")
    private int monedas;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Lob
    @Column(name = "imagen")
    private String imagen;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    //Constructores
    public Jugadores(Integer id, String correo, String usuario, String contrasena, int ranking, int nivel, int monedas, LocalDateTime fechaRegistro, String imagen) {
        this.id = id;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.ranking = ranking;
        this.nivel = nivel;
        this.monedas = monedas;
        this.fechaRegistro = fechaRegistro;
        this.imagen = imagen;
    }

    public Jugadores() {
    }

    //Setters y getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //To string


    @Override
    public String toString() {
        return "Jugadores{" +
                "id=" + id +
                ", correo='" + correo + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", ranking=" + ranking +
                ", nivel=" + nivel +
                ", monedas=" + monedas +
                ", fechaRegistro=" + fechaRegistro +
                ", imagen='" + imagen + '\'' +
                '}';
    }

    //Métodos heredados del UserDetails para gestionar los JWT
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername(){
        return this.usuario;
    }

    @Override
    public String getName() {return this.usuario;}

    @Override
    public  String getPassword(){
        return this.contrasena;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
