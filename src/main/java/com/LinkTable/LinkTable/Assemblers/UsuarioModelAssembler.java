package com.LinkTable.LinkTable.Assemblers;

import com.LinkTable.LinkTable.controller.v2.UsuarioControllerV2;
import com.LinkTable.LinkTable.model.Usuario;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

        @SuppressWarnings("null")
        @Override
        public EntityModel<Usuario> toModel(Usuario usuario) {
                return EntityModel.of(usuario,
                                linkTo(methodOn(UsuarioControllerV2.class)
                                                .getUsuarioById(Long.valueOf(usuario.getId()))).withSelfRel(),
                                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(Long.valueOf(usuario.getId()),
                                                usuario)).withRel("actualizar"),
                                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(Long.valueOf(usuario.getId())))
                                                .withRel("eliminar"),
                                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(Long.valueOf(usuario.getId()),
                                                usuario)).withRel("actualizar-parcial"));
        }
}
