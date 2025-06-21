package com.LinkTable.LinkTable.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.LinkTable.LinkTable.controller.v2.HistorialSesionControllerV2;
import com.LinkTable.LinkTable.model.HistorialSesion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class HistorialSesionModelAssembler implements RepresentationModelAssembler<HistorialSesion, EntityModel<HistorialSesion>> {

        @Override
        public EntityModel<HistorialSesion> toModel(HistorialSesion historialSesion) {
                return EntityModel.of(historialSesion,
                                linkTo(methodOn(HistorialSesionControllerV2.class)
                                                .getHistorialSesionById(Long.valueOf(historialSesion.getId())))
                                                .withSelfRel(),
                                linkTo(methodOn(HistorialSesionControllerV2.class).getAllHistorialSesiones())
                                                .withRel("historialSesiones"),
                                linkTo(methodOn(HistorialSesionControllerV2.class).updateHistorialSesion(
                                                Long.valueOf(historialSesion.getId()),
                                                historialSesion)).withRel("actualizar"),
                                linkTo(methodOn(HistorialSesionControllerV2.class)
                                                .deleteHistorialSesion(Long.valueOf(historialSesion.getId())))
                                                .withRel("eliminar"));
        }
}
