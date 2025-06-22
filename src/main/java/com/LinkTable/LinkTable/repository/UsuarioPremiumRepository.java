package com.LinkTable.LinkTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.LinkTable.LinkTable.model.UsuarioPremium;

public interface UsuarioPremiumRepository extends JpaRepository<UsuarioPremium, Long> {
    void deleteAllByUsuarioId(Long usuarioId);

    @Query("SELECT up FROM UsuarioPremium up JOIN FETCH up.usuario u JOIN FETCH up.plan p")
    List<UsuarioPremium> obtenerUsuariosPremiumConUsuarioYPlan();

}
