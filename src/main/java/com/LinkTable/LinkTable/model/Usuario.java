package com.LinkTable.LinkTable.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Hola
@Entity
@Data 
@Table (name = "usuario")
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String primerNombre;

    @Column(length = 50, nullable = false)
    private String segundoNombre;

    @Column(length = 50, nullable = false)
    private String apellidoMaterno;

    @Column(length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(unique = true, length = 100, nullable = false)
    private String correo;

    @Column(length = 20, nullable = false)
    private String contrasena;

    @Column(nullable = false, length = 2)
    private String esPremium;
}
