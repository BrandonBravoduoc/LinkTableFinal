package com.LinkTable.LinkTable.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.LinkTable.LinkTable.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreo(String correo);    

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Usuario obtenerUsuarioPorCorreo(@Param("correo") String correo);

}
