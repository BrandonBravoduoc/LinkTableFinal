package com.LinkTable.LinkTable.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    HistorialConversionService historialConversionService;

    @MockBean
    HistorialConversionRepository historialConversionRepository;

    public HistorialConversion createHistorialConversion(){
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
}
