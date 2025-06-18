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

    public List<HistorialSesion> findAll(){
        return historialSesionRepository.findAll();
    }

    @SuppressWarnings("deprecation")
    public HistorialSesion getById(long id){
        return historialSesionRepository.getById(id);
    }

    public HistorialSesion save(HistorialSesion historialSesion){
        return historialSesionRepository.save(historialSesion);
    }

    public void delete(long id){
        historialSesionRepository.deleteById(id);
    }

    public List<HistorialSesion>ObtenerHistorialDeUsuario(Long usuarioId){
        return historialSesionRepository.obtenerPorUsuario(usuarioId);
    }

}
