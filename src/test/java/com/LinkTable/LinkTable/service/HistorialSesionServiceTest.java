package com.LinkTable.LinkTable.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.LinkTable.LinkTable.model.HistorialSesion;
import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialSesionRepository;

@SpringBootTest
public class HistorialSesionServiceTest {

    @Autowired
    private HistorialSesionService historialSesionService;

    @SuppressWarnings("removal")
    @MockBean
    private HistorialSesionRepository historialSesionRepository;

    private HistorialSesion createHistorialSesion(){
        return new HistorialSesion(
            1,
            LocalDate.now(),
            1,
            new Usuario()
        );
    }

    @Test
    public void testFindAll(){
        when(historialSesionRepository.findAll()).thenReturn(List.of(createHistorialSesion()));
        List<HistorialSesion> historialSesions = historialSesionService.findAll();
        assertNotNull(historialSesions);
        assertEquals(1, historialSesions.size());
    }

    @Test
    public void testFindById(){
        when(historialSesionRepository.findById(1L)).thenReturn(java.util.Optional.of(createHistorialSesion()));
        HistorialSesion historialSesion = historialSesionService.findById(1L);
        assertNotNull(historialSesion);
        assertEquals(1, historialSesion.getCantidadSesion());
    }

    @Test 
    public void testSave(){
        HistorialSesion historialSesion = createHistorialSesion();
        when(historialSesionRepository.save(historialSesion)).thenReturn(historialSesion);
        HistorialSesion savedHistorialSesion = historialSesionService.save(historialSesion);
        assertNotNull(savedHistorialSesion);
        assertEquals(1, savedHistorialSesion.getCantidadSesion());
    }

    @Test 
    public void testDeleteById(){
        doNothing().when(historialSesionRepository).deleteById(1L);
        historialSesionRepository.deleteById(1L);
        verify(historialSesionRepository, times(1)).deleteById(1L);
    }

}
