package com.LinkTable.LinkTable.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.LinkTable.LinkTable.model.Plan;
import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.model.UsuarioPremium;
import com.LinkTable.LinkTable.repository.UsuarioPremiumRepository;

@SpringBootTest
public class UsuarioPremiumServiceTest {
    @Autowired
    private UsuarioPremiumService usuarioPremiumService;

    @MockBean
    private UsuarioPremiumRepository usuarioPremiumRepository;

    private UsuarioPremium createUsuarioPremium(){
        return new UsuarioPremium(
            1,
            LocalDate.now(),
            1000,
            new Usuario(),
            new Plan()
        );
    }

    @Test
    public void testFindAll(){
        when(usuarioPremiumRepository.findAll()).thenReturn(List.of(createUsuarioPremium()));
        List<UsuarioPremium> usuarioPremium = usuarioPremiumService.findAll();
        assertNotNull(usuarioPremium);
        assertEquals(1, usuarioPremium.size());
    }

    @Test
    public void testFindById(){
        when(usuarioPremiumRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuarioPremium()));
        UsuarioPremium usuarioPremium = usuarioPremiumService.findById(1L);
        assertNotNull(usuarioPremium);
        assertEquals(1000, usuarioPremium.getMontoPago());
    }
    
    @Test
    public void testSave(){
        UsuarioPremium usuarioPremium = createUsuarioPremium();
        when(usuarioPremiumRepository.save(usuarioPremium)).thenReturn(usuarioPremium);
        UsuarioPremium savedUsuariopPremium = usuarioPremiumService.save(usuarioPremium);
        assertNotNull(savedUsuariopPremium);
        assertEquals(1000, savedUsuariopPremium.getMontoPago());
    }    

    @Test
    public void testPatchUsuarioPrPremium(){
        UsuarioPremium existingUsuarioPremium = createUsuarioPremium();
        UsuarioPremium patchData = new UsuarioPremium();
        patchData.setMontoPago(2000);

        when(usuarioPremiumRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuarioPremium));
        when(usuarioPremiumRepository.save(any(UsuarioPremium.class))).thenReturn(existingUsuarioPremium);

        UsuarioPremium patchedUsuarioPremium = usuarioPremiumService.patchUsuarioPremium(1L, patchData);
        assertNotNull(patchedUsuarioPremium);
        assertEquals(2000, patchedUsuarioPremium.getMontoPago());
    }

    @Test
    public void testDeleteById(){
        doNothing().when(usuarioPremiumRepository).deleteById(1L);
        usuarioPremiumRepository.deleteById(1L);
        verify(usuarioPremiumRepository, times(1)).deleteById(1L);
    }

}
