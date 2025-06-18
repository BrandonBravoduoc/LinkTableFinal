package com.LinkTable.LinkTable.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialConversionRepository;
import com.LinkTable.LinkTable.repository.HistorialSesionRepository;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired UsuarioRepository usuarioRepository;

    @Autowired HistorialConversionRepository historialConversionRepository;

    @Autowired HistorialSesionRepository historialInicioRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
   


        for(int i = 0; i < 10; i++){
            Usuario usuario = new Usuario();
            usuario.setId(1 + i);
            usuario.setCorreo(faker.name().fullName());
            usuario.setContrasena(null);
            usuarioRepository.save(usuario);
        }
    }
}
