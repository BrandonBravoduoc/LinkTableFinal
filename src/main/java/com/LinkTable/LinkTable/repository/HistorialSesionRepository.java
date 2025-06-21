package com.LinkTable.LinkTable.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.LinkTable.LinkTable.model.HistorialSesion;
import com.LinkTable.LinkTable.model.Usuario;

public interface HistorialSesionRepository extends JpaRepository<HistorialSesion, Long> {

    Optional<HistorialSesion> findByUsuarioAndFechaInicioSesion(Usuario usuario, LocalDate fechaInicioSesion);

    @Query("SELECT h FROM HistorialSesion h WHERE h.usuario.id = :usuarioId")
    List<HistorialSesion> obtenerPorUsuario(@Param("usuarioId") Long usuarioId);

    void deleteAllByUsuarioId(Long usuarioId);
}
