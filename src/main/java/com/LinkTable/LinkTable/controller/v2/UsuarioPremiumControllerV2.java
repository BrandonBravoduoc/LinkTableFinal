package com.LinkTable.LinkTable.controller.v2;

import com.LinkTable.LinkTable.Assemblers.UsuarioPremiumModelAssembler;
import com.LinkTable.LinkTable.model.UsuarioPremium;
import com.LinkTable.LinkTable.service.UsuarioPremiumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios-premium")
@Tag(name = "Usuarios Premium V2", description = "Aquí estan los usuarios premiums")
public class UsuarioPremiumControllerV2 {

    @Autowired
    private UsuarioPremiumService usuarioPremiumService;

    @Autowired
    private UsuarioPremiumModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama todos los usuarios premium", description = "esta api se encarga de obtener todos los usuarios premium que hay, incluyendo enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioPremium>>> getAllUsuariosPremium() {
        List<EntityModel<UsuarioPremium>> lista = usuarioPremiumService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                lista,
                linkTo(methodOn(UsuarioPremiumControllerV2.class).getAllUsuariosPremium()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un usuario premium por su id", description = "esta api se encarga de obtener a un usuarios premium por id, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<UsuarioPremium>> getUsuarioPremiumById(@PathVariable Long id) {
        UsuarioPremium usuarioPremium = usuarioPremiumService.findById(id);
        if (usuarioPremium == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuarioPremium));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api se encarga de crear a un usuarios premium", description = "Esta api se encarga de crear una nuevo usuario premium, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<UsuarioPremium>> createUsuarioPremium(@RequestBody UsuarioPremium usuarioPremium) {
        UsuarioPremium nuevo = usuarioPremiumService.save(usuarioPremium);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioPremiumControllerV2.class).getUsuarioPremiumById(Long.valueOf(nuevo.getId()))).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza un usuario premium", description = "esta api se encarga de actualizar a un usuario premium existente, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<UsuarioPremium>> updateUsuarioPremium(@PathVariable Long id,
            @RequestBody UsuarioPremium usuarioPremium) {
        usuarioPremium.setId(id.intValue());
        UsuarioPremium actualizado = usuarioPremiumService.save(usuarioPremium);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza parcialmente un usuario premium", description = "esta api se encarga de actualizar parcialmente a un usuario premium existente, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<UsuarioPremium>> patchUsuarioPremium(@PathVariable Long id,
            @RequestBody UsuarioPremium usuarioPremium) {
        UsuarioPremium actualizado = usuarioPremiumService.patchUsuarioPremium(id, usuarioPremium);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina a un usuario premium", description = "esta api se encarga de eliminar a un usuario premium existente, incluyendo enlaces HATEOAS")
    public ResponseEntity<Void> deleteUsuarioPremium(@PathVariable Long id) {
        UsuarioPremium usuarioPremium = usuarioPremiumService.findById(id);
        if (usuarioPremium == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioPremiumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
