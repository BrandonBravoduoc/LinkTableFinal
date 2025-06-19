package com.LinkTable.LinkTable.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.UsuarioRepository;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioServices;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario(){
        return new Usuario(
            1,
            "a",
            "b",
            "c",
            "d",
            "asd",
            "dsd",
            "s"
        );
    }

    @Test
    public void testFindAll(){
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuario = usuarioServices.findAll();
        assertNotNull(usuario);
        assertEquals(1, usuario.size());
    }

    @Test
    public void testFindById(){
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioServices.findById(1L);
        assertNotNull(usuario);
        assertEquals("a", usuario.getPrimerNombre());
    }

    @Test
    public void testSave(){
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedUsuario = usuarioServices.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals("a", savedUsuario.getPrimerNombre());
    }

    @Test
    public void testPatchUsuario(){
        Usuario existingUsuario = createUsuario();
        Usuario patchData = new Usuario();
        patchData.setPrimerNombre("a Actualizado");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        Usuario patchedUsuario = usuarioServices.patchUsuario(1L, patchData);
        assertNotNull(patchedUsuario);
        assertEquals("a Actualizado", patchedUsuario.getPrimerNombre());
    }

    @Test
    public void testDeleteById(){
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioRepository.deleteById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }    
}
