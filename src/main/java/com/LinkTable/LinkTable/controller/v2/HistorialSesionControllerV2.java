package com.LinkTable.LinkTable.controller.v2;

import com.LinkTable.LinkTable.Assemblers.HistorialSesionModelAssembler;
import com.LinkTable.LinkTable.model.HistorialSesion;
import com.LinkTable.LinkTable.service.HistorialSesionService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/historialsesiones")
@Tag(name = "Historiales V2", description = "Aqui estan los historiales de sesion")
public class HistorialSesionControllerV2 {

    @Autowired
    private HistorialSesionService historialSesionService;

    @Autowired
    private HistorialSesionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<HistorialSesion>>> getAllHistorialSesiones() {
        List<EntityModel<HistorialSesion>> sesiones = historialSesionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (sesiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                sesiones,
                linkTo(methodOn(HistorialSesionControllerV2.class).getAllHistorialSesiones()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<HistorialSesion>> getHistorialSesionById(@PathVariable Long id) {
        HistorialSesion historialSesion = historialSesionService.findById(id);
        if (historialSesion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(historialSesion));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<HistorialSesion>> createHistorialSesion(
            @RequestBody HistorialSesion historialSesion) {
        HistorialSesion newSesion = historialSesionService.save(historialSesion);
        return ResponseEntity
                .created(linkTo(methodOn(HistorialSesionControllerV2.class)
                        .getHistorialSesionById(Long.valueOf(newSesion.getId())))
                        .toUri())
                .body(assembler.toModel(newSesion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<HistorialSesion>> updateHistorialSesion(@PathVariable Long id,
            @RequestBody HistorialSesion historialSesion) {
        historialSesion.setId(id.intValue());
        HistorialSesion updatedSesion = historialSesionService.save(historialSesion);
        return ResponseEntity.ok(assembler.toModel(updatedSesion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteHistorialSesion(@PathVariable Long id) {
        HistorialSesion historialSesion = historialSesionService.findById(id);
        if (historialSesion == null) {
            return ResponseEntity.notFound().build();
        }
        historialSesionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
