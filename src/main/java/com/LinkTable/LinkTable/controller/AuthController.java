
package com.LinkTable.LinkTable.controller;


import com.LinkTable.LinkTable.model.LoginRequest;
import com.LinkTable.LinkTable.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String mensaje = authService.login(request.getCorreo(), request.getContrasena());
        return ResponseEntity.ok(mensaje);
    }
}
