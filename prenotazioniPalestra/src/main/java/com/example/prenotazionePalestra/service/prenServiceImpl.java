package com.example.prenotazionePalestra.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prenotazionePalestra.entity.Prenotazione;
import com.example.prenotazionePalestra.repository.PrenRepository;


@Service
public class prenServiceImpl implements prenService{
    
    @Autowired
    private PrenRepository pRepo;

    @Override
    public Iterable<Prenotazione> findPrenOggi(LocalDate todaysDate){
        return pRepo.findPrenOggi(todaysDate);
    }

}
