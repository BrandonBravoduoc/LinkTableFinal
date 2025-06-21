package com.LinkTable.LinkTable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LinkTable.LinkTable.model.UsuarioPremium;

public interface UsuarioPremiumRepository extends JpaRepository<UsuarioPremium, Long> {
    void deleteAllByUsuarioId(Long usuarioId);
}
