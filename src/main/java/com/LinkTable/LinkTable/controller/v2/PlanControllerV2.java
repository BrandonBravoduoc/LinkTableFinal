package com.LinkTable.LinkTable.controller.v2;

import com.LinkTable.LinkTable.Assemblers.PlanModelAssembler;
import com.LinkTable.LinkTable.model.Plan;
import com.LinkTable.LinkTable.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v2/planes")
@Tag(name = "Plan V2", description = "Aquí estan los planes")
public class PlanControllerV2 {

    @Autowired
    private PlanService planService;

    @Autowired
    private PlanModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un planes por su id", description = "esta api se encarga de obtener a un plan por id")
    public ResponseEntity<CollectionModel<EntityModel<Plan>>> getAllPlanes() {
        List<EntityModel<Plan>> planes = planService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (planes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                planes,
                linkTo(methodOn(PlanControllerV2.class).getAllPlanes()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api llama a un planes por su id", description = "esta api se encarga de obtener a un plan por id")
    public ResponseEntity<EntityModel<Plan>> getPlanById(@PathVariable Long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(plan));
    }

    @PostMapping(value = "registrar", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api se encarga de crear un plan", description = "Esta api se encarga de crear una nuevo plan")
    public ResponseEntity<EntityModel<Plan>> createPlan(@RequestBody Plan plan) {
        Plan nuevoPlan = planService.save(plan);
        return ResponseEntity
                .created(linkTo(methodOn(PlanControllerV2.class).getPlanById(Long.valueOf(nuevoPlan.getId()))).toUri())
                .body(assembler.toModel(nuevoPlan));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza un plan", description = "esta api se encarga de actualizar un plan existente")
    public ResponseEntity<EntityModel<Plan>> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
        plan.setId(id.intValue());
        Plan actualizado = planService.save(plan);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api actualiza parcialmente un plan", description = "esta api se encarga de actualizar parcialmente a un plan existente")
    public ResponseEntity<EntityModel<Plan>> patchPlan(@PathVariable Long id, @RequestBody Plan plan) {
        Plan actualizado = planService.patchPlan(id, plan);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Esta api elimina a un plan", description = "esta api se encarga de eliminar un plan existente")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
