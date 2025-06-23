package com.LinkTable.LinkTable.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.HistorialSesion;
import com.LinkTable.LinkTable.repository.HistorialSesionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistorialSesionService {

    @Autowired
    HistorialSesionRepository historialSesionRepository;

    public List<HistorialSesion> findAll() {
        return historialSesionRepository.findAll();
    }

    public HistorialSesion findById(long id) {
        return historialSesionRepository.findById(id).orElse(null);
    }

    public HistorialSesion save(HistorialSesion historialSesion) {
        return historialSesionRepository.save(historialSesion);
    }

    public void delete(long id) {
        historialSesionRepository.deleteById(id);
    }

    public List<HistorialSesion> ObtenerHistorialDeUsuario(Long usuarioId) {
        return historialSesionRepository.obtenerPorUsuario(usuarioId);
    }

    public void eliminarSesionesPorCorreo(Integer usuarioId, String correo) {
        historialSesionRepository.deleteAllByUsuario_IdAndUsuario_Correo(usuarioId, correo);
    }

}
