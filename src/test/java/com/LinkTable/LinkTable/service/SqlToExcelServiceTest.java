package com.LinkTable.LinkTable.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.HistorialConversionRepository;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SqlToExcelServiceTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private HistorialConversionRepository historialConversionRepository;

    @Autowired
    private SqlToExcelService sqlToExcelService;

    @Test
    void testConvertirSqlAExcel() throws Exception {
        // Simular usuario
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setPrimerNombre("Brandon");
        usuario.setCorreo("test@correo.com");

        Mockito.when(usuarioRepository.findByCorreo("test@correo.com"))
                .thenReturn(java.util.Optional.of(usuario));

        // Simular archivo SQL
        String sql = "INSERT INTO ejemplo (col1, col2) VALUES ('valor1', 'valor2');";
        MockMultipartFile archivoSimulado = new MockMultipartFile(
                "archivo", "ejemplo.sql", "text/plain", sql.getBytes());

        // Ejecutar método
        byte[] excelGenerado = sqlToExcelService.convertirSqlAExcel(archivoSimulado, "test@correo.com");

        // Validaciones
        assertNotNull(excelGenerado);
        assertTrue(excelGenerado.length > 0);

        // Leer y validar Excel generado
        try (InputStream in = new ByteArrayInputStream(excelGenerado);
                XSSFWorkbook workbook = new XSSFWorkbook(in)) {

            Sheet sheet = workbook.getSheet("DatosSQL");
            assertNotNull(sheet);

            Row header = sheet.getRow(0);
            assertEquals("col1", header.getCell(0).getStringCellValue());
            assertEquals("col2", header.getCell(1).getStringCellValue());

            Row dataRow = sheet.getRow(1);
            assertEquals("valor1", dataRow.getCell(0).getStringCellValue());
            assertEquals("valor2", dataRow.getCell(1).getStringCellValue());
        }
    }
}