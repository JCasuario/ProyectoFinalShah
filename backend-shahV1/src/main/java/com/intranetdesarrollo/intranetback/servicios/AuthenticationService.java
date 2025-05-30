package com.intranetdesarrollo.intranetback.servicios;

import com.intranetdesarrollo.intranetback.DTOs.AdminLogDTO;
import com.intranetdesarrollo.intranetback.DTOs.JugadorLogDTO;
import com.intranetdesarrollo.intranetback.DTOs.JugadorRegistroDTO;
import com.intranetdesarrollo.intranetback.modelos.Administradores;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import com.intranetdesarrollo.intranetback.repositorios.AdministradoresRepositorio;
import com.intranetdesarrollo.intranetback.repositorios.JugadoresRepositorio;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JugadoresRepositorio jugadorRepositorio;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final AdministradoresRepositorio administradoresRepositorio;

    public AuthenticationService(
            JugadoresRepositorio jugadorRepositorio,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            AdministradoresRepositorio admin
    ) {
        this.authenticationManager = authenticationManager;
        this.jugadorRepositorio = jugadorRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.administradoresRepositorio = admin;
    }

    public Boolean signup(JugadorRegistroDTO input) {
        Jugadores jugador = new Jugadores();
        jugador.setUsuario(input.usuario());
        jugador.setCorreo(input.correo());
        jugador.setImagen("/Perfil/zorro.png");
        jugador.setContrasena(passwordEncoder.encode(input.contrasena()));
        jugadorRepositorio.save(jugador);

        try{
        return true;
        } catch (Exception e){
            return false;
        }
    }

    public Jugadores authenticate(JugadorLogDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsuario(),
                        input.getContrasena()
                )
        );

        return jugadorRepositorio.findByUsuario(input.getUsuario())
                .orElseThrow();
    }

    //ESTO ES UNA SOLUCIÓN TEMPORAL HABRÁ QUE CORREGIRLA EN EL FUTURO AÑADIENDO UN NUEVO CAMPO ROL A JUGADORES
    public Jugadores authenticateAdmin(JugadorLogDTO input) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsuario(),
                        input.getContrasena()
                )
        );

        Administradores admin = administradoresRepositorio.findByUsuario(input.getUsuario());
        if(admin != null) {
            System.out.println("Admin autenticado: " + admin.getUsuario());
            return this.jugadorRepositorio.findByUsuario(input.getUsuario()).orElseThrow();
        }
        throw new Exception("No hay entradas");
    }
}