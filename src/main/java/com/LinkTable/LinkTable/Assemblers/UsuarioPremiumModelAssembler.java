package com.LinkTable.LinkTable.Assemblers;

import com.LinkTable.LinkTable.controller.v2.UsuarioPremiumControllerV2;
import com.LinkTable.LinkTable.model.UsuarioPremium;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioPremiumModelAssembler implements RepresentationModelAssembler<UsuarioPremium, EntityModel<UsuarioPremium>> {

        @SuppressWarnings("null")
        @Override
        public EntityModel<UsuarioPremium> toModel(UsuarioPremium usuarioPremium) {
                return EntityModel.of(usuarioPremium,
                                linkTo(methodOn(UsuarioPremiumControllerV2.class).getUsuarioPremiumById(Long.valueOf(usuarioPremium.getId()))).withSelfRel(),
                                linkTo(methodOn(UsuarioPremiumControllerV2.class).getAllUsuariosPremium()).withRel("usuariosPremium"),
                                linkTo(methodOn(UsuarioPremiumControllerV2.class).updateUsuarioPremium(Long.valueOf(usuarioPremium.getId()),usuarioPremium)).withRel("actualizar"),
                                linkTo(methodOn(UsuarioPremiumControllerV2.class).deleteUsuarioPremium(Long.valueOf(usuarioPremium.getId()))).withRel("eliminar"),
                                linkTo(methodOn(UsuarioPremiumControllerV2.class).patchUsuarioPremium(Long.valueOf(usuarioPremium.getId()),usuarioPremium)).withRel("actualizar-parcial"));
        }
}
