package com.LinkTable.LinkTable.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.HistorialConversion;
import com.LinkTable.LinkTable.repository.HistorialConversionRepository;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class HistorialConversionService {

    @Autowired
    HistorialConversionRepository historialConversionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<HistorialConversion> findAll() {
        return historialConversionRepository.findAll();
    }

    public HistorialConversion findById(long id) {
        return historialConversionRepository.findById(id).orElse(null);
    }

    public HistorialConversion save(HistorialConversion historialConversion) {
        return historialConversionRepository.save(historialConversion);
    }

    public void delete(long id) {
        historialConversionRepository.deleteById(id);
    }

    // Query
    public List<HistorialConversion> obtenerHistorialPorUsuario(Long usuarioId) {
        return historialConversionRepository.obtenerHistorialPorUsuario(usuarioId);
    }

    public void eliminarConversionesPorCorreo(Integer usuarioId, String correo) {
        historialConversionRepository.deleteAllByUsuario_IdAndUsuario_Correo(usuarioId, correo);
    }

}
