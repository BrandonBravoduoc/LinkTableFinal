package com.LinkTable.LinkTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.LinkTable.LinkTable.model.HistorialConversion;

public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Long>{

    @Query("SELECT h FROM HistorialConversion h WHERE h.usuario.id = :usuarioId")
    List<HistorialConversion> obtenerHistorialPorUsuario(@Param("usuarioId") Long usuarioId);
}
