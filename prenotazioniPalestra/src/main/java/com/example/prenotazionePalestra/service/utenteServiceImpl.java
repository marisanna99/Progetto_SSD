package com.example.prenotazionePalestra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prenotazionePalestra.repository.UtenteRepository;

@Service
public class utenteServiceImpl implements utenteService{
    
    @Autowired
    private UtenteRepository uRepo;

    @Override
    public Integer findMatricola(String username){
        return uRepo.findMatricola(username);
    }

}
