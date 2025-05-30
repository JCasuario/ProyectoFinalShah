package com.intranetdesarrollo.intranetback.controladores;


import com.intranetdesarrollo.intranetback.DTOs.JugadorLogDTO;
import com.intranetdesarrollo.intranetback.DTOs.JugadorRegistroDTO;
import com.intranetdesarrollo.intranetback.configuration.LoginResponse;
import com.intranetdesarrollo.intranetback.modelos.Jugadores;
import com.intranetdesarrollo.intranetback.servicios.AuthenticationService;
import com.intranetdesarrollo.intranetback.servicios.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationControlador {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationControlador(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public Boolean register(@RequestBody JugadorRegistroDTO registerUserDto) {

        return authenticationService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody JugadorLogDTO loginUserDto) {
        try{

        Jugadores authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity<LoginResponse> authenticateAdmin(@RequestBody JugadorLogDTO loginUserDto) {
        try{

        Jugadores authenticatedUser = authenticationService.authenticateAdmin(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
