package com.LinkTable.LinkTable.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LinkTable.LinkTable.model.Plan;
import com.LinkTable.LinkTable.service.PlanService;



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
@RequestMapping("api/v1/planes")
@Tag(name = "Plan", description = "Aquí estan los planes")
public class PlanController {

    @Autowired
    private PlanService planService;


    @GetMapping
    @Operation(summary = "Esta api llama todos los planes", description = "esta api se encarga de obtener todos los planes que hay")
    public ResponseEntity<List<Plan>>listar(){
        List<Plan> plan = planService.findAll();
        if(plan.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(plan);
    }    

    @GetMapping("/{id}")
    @Operation(summary = "Esta api llama a un planes por su id", description = "esta api se encarga de obtener a un plan por id")
    public ResponseEntity<Plan> buscar(@PathVariable long id) {
        try{
            Plan plan = planService.findById(id);
            return ResponseEntity.ok(plan);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    @Operation(summary = "Esta api se encarga de crear un plan", description = "Esta api se encarga de crear una nuevo plan")
    public ResponseEntity<?> registrar(@RequestBody Plan plan) {
        try{
        Plan UpdatePlan = planService.save(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(UpdatePlan);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puedo guardar");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Esta api actualiza plan", description = "esta api se encarga de actualizar un plan existente")
    public ResponseEntity<Plan> updateEstudiante(@RequestBody Plan plan) {
        Plan updatedPlan = planService.save(plan);
        if (updatedPlan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plan);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Esta api actualiza parcialmente un plan", description = "esta api se encarga de actualizar parcialmente a un plan existente")
    public ResponseEntity<Plan> patchUsuario(@PathVariable Long id, @RequestBody Plan plan) {
        try {
            Plan updatedPlan = planService.patchPlan(id, plan);
            return ResponseEntity.ok(updatedPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Esta api elimina a un plan", description = "esta api se encarga de eliminar un plan existente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try { 
            planService.delete(id);             
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
