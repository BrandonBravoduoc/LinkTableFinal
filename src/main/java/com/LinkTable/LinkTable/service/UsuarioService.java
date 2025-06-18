package com.LinkTable.LinkTable.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.Usuario;
import com.LinkTable.LinkTable.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario>findAll(){
        return usuarioRepository.findAll();
    }

    @SuppressWarnings("deprecation")
    public Usuario getById(Long id){
        return usuarioRepository.getById(id);
    }
    
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void delete(long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario patchUsuario(Long id, Usuario usuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuarioToUpdate = usuarioOptional.get();

            if(usuario.getPrimerNombre()!= null){
                usuarioToUpdate.setPrimerNombre(usuario.getPrimerNombre());
            }
            if(usuario.getSegundoNombre()!= null){
                usuarioToUpdate.setSegundoNombre(usuario.getSegundoNombre());
            }
            if(usuario.getApellidoMaterno()!= null){
                usuarioToUpdate.setApellidoMaterno(usuario.getApellidoMaterno());
            }
            if(usuario.getApellidoPaterno()!= null){
                usuarioToUpdate.setApellidoPaterno(usuario.getApellidoPaterno());
            }
            if(usuario.getCorreo()!= null){
                usuarioToUpdate.setCorreo(usuario.getCorreo());
            }
            if(usuario.getContrasena()!= null){
                usuarioToUpdate.setContrasena(usuario.getContrasena());
            }
            if(usuario.getEsPremium()!=null){
                usuarioToUpdate.setEsPremium(usuario.getEsPremium());
            }
            return usuarioRepository.save(usuarioToUpdate);
        }else{
        return null;
        }
    }
}