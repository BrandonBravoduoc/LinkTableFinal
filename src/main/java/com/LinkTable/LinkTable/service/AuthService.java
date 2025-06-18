package com.LinkTable.LinkTable.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.HistorialSesion;
import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialSesionRepository;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistorialSesionRepository historialRepository;

    public String login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        LocalDate hoy = LocalDate.now();
        Optional<HistorialSesion> existente = historialRepository.findByUsuarioAndFechaInicioSesion(usuario, hoy);

        if (existente.isPresent()) {
            HistorialSesion historial = existente.get();
            historial.setCantidadSesion(historial.getCantidadSesion() + 1);
            historialRepository.save(historial);
        } else {
            HistorialSesion nuevoHistorial = new HistorialSesion();
            nuevoHistorial.setFechaInicioSesion(hoy);
            nuevoHistorial.setCantidadSesion(1);
            nuevoHistorial.setUsuario(usuario);
            historialRepository.save(nuevoHistorial);
        }

        return "Login exitoso";
    }
}
