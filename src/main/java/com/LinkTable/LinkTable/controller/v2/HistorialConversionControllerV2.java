package com.LinkTable.LinkTable.controller.v2;

import com.LinkTable.LinkTable.Assemblers.HistorialConversionModelAssembler;
import com.LinkTable.LinkTable.model.HistorialConversion;
import com.LinkTable.LinkTable.service.HistorialConversionService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/historialConversion")
@Tag(name = "Historiales V2", description = "Aqui estan los historiales de conversion")
public class HistorialConversionControllerV2 {

    @Autowired
    private HistorialConversionService historialConversionService;

    @Autowired
    private HistorialConversionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
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
    public ResponseEntity<EntityModel<HistorialConversion>> getHistorialConversionById(@PathVariable Long id) {
        HistorialConversion hc = historialConversionService.findById(id);
        if (hc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(hc));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
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

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<HistorialConversion>> updateHistorialConversion(@PathVariable Long id,
            @RequestBody HistorialConversion historialConversion) {
        historialConversion.setId(id.intValue());
        HistorialConversion updated = historialConversionService.save(historialConversion);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteHistorialConversion(@PathVariable Long id) {
        HistorialConversion hc = historialConversionService.findById(id);
        if (hc == null) {
            return ResponseEntity.notFound().build();
        }
        historialConversionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
