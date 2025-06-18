package com.LinkTable.LinkTable.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LinkTable.LinkTable.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreo(String correo);    
}
