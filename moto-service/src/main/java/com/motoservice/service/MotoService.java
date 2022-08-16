package com.motoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.motoservice.entidades.Moto;
import com.motoservice.repository.MotoRespository;

@Service
public class MotoService {
    @Autowired
    private MotoRespository motoRepository;
    public List<Moto> getAll(){
        return motoRepository.findAll();
    }

    public Moto getMotoById(int id){
        return motoRepository.findById(id).orElse(null);
    }

    public Moto save(Moto moto){
        Moto newmoto = motoRepository.save(moto);
        return newmoto;
    }

    public List<Moto> byUsuarioId(int usuarioId){
        return motoRepository.findByUsuarioId(usuarioId);
    }
}