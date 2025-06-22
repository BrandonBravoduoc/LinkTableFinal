package com.LinkTable.LinkTable.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialConversionRepository;
import com.LinkTable.LinkTable.repository.HistorialSesionRepository;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private HistorialSesionRepository historialSesionRepository;

    @Autowired
    private HistorialConversionRepository historialConversionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        //Eliminación por cascada :D
        if (usuario != null) {
            // 1. Eliminar sesiones
            historialSesionRepository.deleteAllByUsuarioId(id);

            // 2. Eliminar conversiones
            historialConversionRepository.deleteAllByUsuarioId(id);

            // 3. Eliminar el usuario
            usuarioRepository.delete(usuario);
        }
    }

    public Usuario patchUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioToUpdate = usuarioOptional.get();

            if (usuario.getPrimerNombre() != null) {
                usuarioToUpdate.setPrimerNombre(usuario.getPrimerNombre());
            }
            if (usuario.getSegundoNombre() != null) {
                usuarioToUpdate.setSegundoNombre(usuario.getSegundoNombre());
            }
            if (usuario.getApellidoMaterno() != null) {
                usuarioToUpdate.setApellidoMaterno(usuario.getApellidoMaterno());
            }
            if (usuario.getApellidoPaterno() != null) {
                usuarioToUpdate.setApellidoPaterno(usuario.getApellidoPaterno());
            }
            if (usuario.getCorreo() != null) {
                usuarioToUpdate.setCorreo(usuario.getCorreo());
            }
            if (usuario.getContrasena() != null) {
                usuarioToUpdate.setContrasena(usuario.getContrasena());
            }
            if (usuario.getEsPremium() != null) {
                usuarioToUpdate.setEsPremium(usuario.getEsPremium());
            }
            return usuarioRepository.save(usuarioToUpdate);
        } else {
            return null;
        }
    }
}