package com.LinkTable.LinkTable.Assemblers;

import com.LinkTable.LinkTable.controller.v2.PlanControllerV2;
import com.LinkTable.LinkTable.model.Plan;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlanModelAssembler implements RepresentationModelAssembler<Plan, EntityModel<Plan>> {

        @Override
        public EntityModel<Plan> toModel(Plan plan) {
                return EntityModel.of(plan,
                                linkTo(methodOn(PlanControllerV2.class).getPlanById(Long.valueOf(plan.getId()))).withSelfRel(),
                                linkTo(methodOn(PlanControllerV2.class).getAllPlanes()).withRel("planes"),
                                linkTo(methodOn(PlanControllerV2.class).updatePlan(Long.valueOf(plan.getId()), plan)).withRel("actualizar"),
                                linkTo(methodOn(PlanControllerV2.class).deletePlan(Long.valueOf(plan.getId()))).withRel("eliminar"),
                                linkTo(methodOn(PlanControllerV2.class).patchPlan(Long.valueOf(plan.getId()), plan)).withRel("actualizar-parcial"));
        }
}
