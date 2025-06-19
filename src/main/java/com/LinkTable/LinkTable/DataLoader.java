package com.LinkTable.LinkTable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        for(int i = 0; i < 10; i++){
            Usuario usuario = new Usuario();
            usuario.setPrimerNombre(faker.name().firstName());
            usuario.setSegundoNombre(faker.name().firstName());
            usuario.setApellidoPaterno(faker.name().lastName());
            usuario.setApellidoMaterno(faker.name().lastName());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password(8, 20));
            usuario.setEsPremium(faker.bool().bool() ? "1" : "0");
            usuarioRepository.save(usuario);
        }
    }
}
