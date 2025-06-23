package com.LinkTable.LinkTable.controller.v2;

import com.LinkTable.LinkTable.Assemblers.HistorialConversionModelAssembler;
import com.LinkTable.LinkTable.model.HistorialConversion;
import com.LinkTable.LinkTable.service.HistorialConversionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/historiales-Conversiones")
@Tag(name = "Historiales Conversiones V2", description = "Aquí estan los historiales de conversion")
public class HistorialConversionControllerV2 {

    @Autowired
    private HistorialConversionService historialConversionService;

    @Autowired
    private HistorialConversionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a todo los historiales de conversion v2", description = "esta api se encarga de obtener todos los historiles que hay, incluyendo enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<HistorialConversion>>> getAllHistorialConversions() {
        List<EntityModel<HistorialConversion>> list = historialConversionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                list,
                linkTo(methodOn(HistorialConversionControllerV2.class).getAllHistorialConversions()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un historial por su id", description = "esta api se encarga de obtener a un historial por id, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<HistorialConversion>> getHistorialConversionById(@PathVariable Long id) {
        HistorialConversion hc = historialConversionService.findById(id);
        if (hc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(hc));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api se encarga de crear a un historial", description = "Esta api se encarga de crear una nuevo historial, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<HistorialConversion>> createHistorialConversion(
            @RequestBody HistorialConversion historialConversion) {
        HistorialConversion newHC = historialConversionService.save(historialConversion);
        return ResponseEntity
                .created(linkTo(
                        methodOn(HistorialConversionControllerV2.class)
                                .getHistorialConversionById(Long.valueOf(newHC.getId())))
                        .toUri())
                .body(assembler.toModel(newHC));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina a un historial", description = "esta api se encarga de eliminar a un historial existente, incluyendo enlaces HATEOAS")
    public ResponseEntity<Void> deleteHistorialConversion(@PathVariable Long id) {
        HistorialConversion hc = historialConversionService.findById(id);
        if (hc == null) {
            return ResponseEntity.notFound().build();
        }
        historialConversionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/eliminar-por-correo", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina conversiones por ID de usuario y correo", description = "Elimina todas las conversiones que coincidan con el ID de usuario y correo")
    public ResponseEntity<Void> eliminarConversionesPorCorreo(
            @RequestParam Integer usuarioId,
            @RequestParam String correo) {

        historialConversionService.eliminarConversionesPorCorreo(usuarioId, correo);
        return ResponseEntity.noContent().build();
    }
}
