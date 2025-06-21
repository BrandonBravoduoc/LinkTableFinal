package com.LinkTable.LinkTable.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.LinkTable.LinkTable.model.UsuarioPremium;
import com.LinkTable.LinkTable.service.UsuarioPremiumService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/usuariosPremium")
@Tag(name = "Usuarios", description = "Aquí estan los usuarios")
public class UsuarioPremiumController {

    @Autowired
    private UsuarioPremiumService usuarioPremiumService;


    @GetMapping
    @Operation(summary = "Esta api llama todos los usuarios premium", description = "esta api se encarga de obtener todos los usuarios premium que hay")
    public ResponseEntity<List<UsuarioPremium>>listar(){
        List<UsuarioPremium> usuario = usuarioPremiumService.findAll();
        if(usuario.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
    }    

    @GetMapping("/{id}")
    @Operation(summary = "Esta api llama a un usuario premium por su id", description = "esta api se encarga de obtener a un usuarios premium por id")
    public ResponseEntity<UsuarioPremium> buscar(@PathVariable long id) {
        try{
            UsuarioPremium usuarioPremium = usuarioPremiumService.findById(id);
            return ResponseEntity.ok(usuarioPremium);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    @Operation(summary = "Esta api se encarga de crear a un usuarios premium", description = "Esta api se encarga de crear una nuevo usuario premium")
    public ResponseEntity<?> registrar(@RequestBody UsuarioPremium usuarioPremium) {
        try{
        UsuarioPremium nuevoUsuarioPremium = usuarioPremiumService.save(usuarioPremium);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuarioPremium);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puedo guardar");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Esta api actualiza un usuario premium", description = "esta api se encarga de actualizar a un usuario premium existente")
    public ResponseEntity<UsuarioPremium> updateEstudiante(@RequestBody UsuarioPremium usuarioPremium) {
        UsuarioPremium updatedUsuarioPremium = usuarioPremiumService.save(usuarioPremium);
        if (updatedUsuarioPremium == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUsuarioPremium);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Esta api actualiza parcialmente un usuario premium", description = "esta api se encarga de actualizar parcialmente a un usuario premium existente")
    public ResponseEntity<UsuarioPremium> patchUsuario(@PathVariable Long id, @RequestBody UsuarioPremium usuarioPremium) {
        try {
            UsuarioPremium updatedUsuario = usuarioPremiumService.patchUsuarioPremium(id, usuarioPremium);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Esta api elimina a un usuario premium", description = "esta api se encarga de eliminar a un usuario premium existente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try { 
            usuarioPremiumService.delete(id);             
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
