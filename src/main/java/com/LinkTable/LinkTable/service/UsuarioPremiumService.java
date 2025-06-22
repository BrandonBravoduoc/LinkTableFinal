package com.LinkTable.LinkTable.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.UsuarioPremium;
import com.LinkTable.LinkTable.repository.UsuarioPremiumRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioPremiumService {

    @Autowired
    UsuarioPremiumRepository usuarioPremiumRepository;

    public List<UsuarioPremium>findAll(){
        return usuarioPremiumRepository.findAll();
    }

    public UsuarioPremium findById(long id){
        return usuarioPremiumRepository.findById(id).orElse(null);
    }

    public UsuarioPremium save(UsuarioPremium usuarioPremium){
        return usuarioPremiumRepository.save(usuarioPremium);
    }

    public void delete(long id){
        usuarioPremiumRepository.deleteById(id);
    }

    //Query
    public List<UsuarioPremium> obtenerUsuariosPremiumConUsuarioYPlan() {
        return usuarioPremiumRepository.obtenerUsuariosPremiumConUsuarioYPlan();
    }

    public UsuarioPremium patchUsuarioPremium(Long id, UsuarioPremium usuarioPremium){
        Optional<UsuarioPremium> usuarioOptional = usuarioPremiumRepository.findById(id);
        if(usuarioOptional.isPresent()){
            
            UsuarioPremium usuarioToUpdate = usuarioOptional.get();
            if(usuarioPremium.getUsuario()!= null){
                usuarioToUpdate.setUsuario(usuarioPremium.getUsuario());
            }

            if(usuarioPremium.getPlan()!= null){
                usuarioToUpdate.setPlan(usuarioPremium.getPlan());
            }

            if(usuarioPremium.getMontoPago()!= null){
                usuarioToUpdate.setMontoPago(usuarioPremium.getMontoPago());
            }

            if(usuarioPremium.getFechaCompra()!= null){
                usuarioPremium.setFechaCompra(usuarioPremium.getFechaCompra());
            }
            return usuarioPremiumRepository.save(usuarioToUpdate);

        }else{
            return null;
        }
    }


}
