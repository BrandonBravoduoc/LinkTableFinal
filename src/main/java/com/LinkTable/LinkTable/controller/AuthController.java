
package com.LinkTable.LinkTable.controller;

import com.LinkTable.LinkTable.model.LoginRequest;
import com.LinkTable.LinkTable.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Autenticación", description = "Aqui esta el autenticación")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Esta api permite iniciar sesión", description = "esta api se encarga de iniciar sesíon")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String mensaje = authService.login(request.getCorreo(), request.getContrasena());
        return ResponseEntity.ok(mensaje);
    }
}
