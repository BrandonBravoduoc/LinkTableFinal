package com.LinkTable.LinkTable.Assemblers;

import com.LinkTable.LinkTable.controller.v2.HistorialConversionControllerV2;
import com.LinkTable.LinkTable.model.HistorialConversion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HistorialConversionModelAssembler implements RepresentationModelAssembler<HistorialConversion, EntityModel<HistorialConversion>> {

        @SuppressWarnings("null")
        @Override
        public EntityModel<HistorialConversion> toModel(HistorialConversion historialConversion) {
                return EntityModel.of(historialConversion,
                                linkTo(methodOn(HistorialConversionControllerV2.class).getHistorialConversionById(Long.valueOf(historialConversion.getId()))).withSelfRel(),
                                linkTo(methodOn(HistorialConversionControllerV2.class).getAllHistorialConversions()).withRel("historialConversions"),
                                linkTo(methodOn(HistorialConversionControllerV2.class).deleteHistorialConversion(Long.valueOf(historialConversion.getId()))).withRel("eliminar"));
        }
}
