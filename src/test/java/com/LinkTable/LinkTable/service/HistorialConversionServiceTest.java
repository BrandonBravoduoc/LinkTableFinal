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

import com.LinkTable.LinkTable.model.HistorialConversion;
import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialConversionRepository;

@SpringBootTest
public class HistorialConversionServiceTest {

    @Autowired
    private HistorialConversionService historialConversionService;

    @MockBean
    private HistorialConversionRepository historialConversionRepository;

    private HistorialConversion createHistorialConversion(){
        return new HistorialConversion(
            1, 
            "excel", 
            "hola",
            new Usuario(),
            LocalDate.now()
        );
    }

    @Test
    public void testFindAll(){
        when(historialConversionRepository.findAll()).thenReturn(List.of(createHistorialConversion()));
        List<HistorialConversion> historialConversions =historialConversionService.findAll();
        assertNotNull(historialConversions);
        assertEquals(1, historialConversions.size());
    }

    @Test
    public void testFindById(){
        when(historialConversionRepository.findById(1L)).thenReturn(java.util.Optional.of(createHistorialConversion()));
        HistorialConversion historialConversion = historialConversionService.findById(1L);
        assertNotNull(historialConversion);
        assertEquals("excel", historialConversion.getTipoConversion());

    }

    @Test
    public void testSave(){
        HistorialConversion historialConversion = createHistorialConversion();
        when(historialConversionRepository.save(historialConversion)).thenReturn(historialConversion);
        HistorialConversion savedHistorialConversion = historialConversionService.save(historialConversion);
        assertNotNull(savedHistorialConversion);
        assertEquals("excel", savedHistorialConversion.getTipoConversion());
    }

    @Test
    public void testDeleteById(){
        doNothing().when(historialConversionRepository).deleteById(1L);
        historialConversionRepository.deleteById(1L);
        verify(historialConversionRepository, times(1)).deleteById(1L);
    }
}
