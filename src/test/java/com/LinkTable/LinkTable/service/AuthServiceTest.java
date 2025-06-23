package com.LinkTable.LinkTable.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.LinkTable.LinkTable.model.HistorialSesion;
import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialSesionRepository;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    @SuppressWarnings("removal")
    @MockBean
    private UsuarioRepository usuarioRepository;

    @SuppressWarnings("removal")
    @MockBean
    private HistorialSesionRepository historialRepository;

    @Autowired
    private AuthService authService;

    @Test
    void testLoginConHistorialExistente() {

        String correo = "usuario@falso.com";
        String contrasena = "1234";
        LocalDate hoy = LocalDate.now();

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);

        HistorialSesion historial = new HistorialSesion();
        historial.setId(1);
        historial.setFechaInicioSesion(hoy);
        historial.setCantidadSesion(2);
        historial.setUsuario(usuario);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(historialRepository.findByUsuarioAndFechaInicioSesion(usuario, hoy))
                .thenReturn(Optional.of(historial));

        String resultado = authService.login(correo, contrasena);

        assertEquals("Login exitoso", resultado);
        verify(historialRepository).save(any(HistorialSesion.class));
    }

    @Test
    void testLoginSinHistorialPrevio() {
        String correo = "nuevo@correo.com";
        String contrasena = "abcd";

        Usuario usuario = new Usuario();
        usuario.setId(2);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(historialRepository.findByUsuarioAndFechaInicioSesion(eq(usuario), any()))
                .thenReturn(Optional.empty());

        String resultado = authService.login(correo, contrasena);

        assertEquals("Login exitoso", resultado);
        verify(historialRepository).save(any(HistorialSesion.class));
    }

    @Test
    void testLoginConContrasenaIncorrecta() {
        String correo = "usuario@falso.com";
        String contrasena = "incorrecta";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContrasena("correcta");

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(correo, contrasena);
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
    }

    @Test
    void testLoginUsuarioNoEncontrado() {
        String correo = "noexiste@correo.com";

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(correo, "cualquiera");
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }
}