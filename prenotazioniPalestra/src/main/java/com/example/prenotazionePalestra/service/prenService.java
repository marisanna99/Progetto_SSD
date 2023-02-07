package com.example.prenotazionePalestra.service;

import java.time.LocalDate;

import com.example.prenotazionePalestra.entity.Prenotazione;

public interface prenService {
    Iterable<Prenotazione> findPrenOggi(LocalDate todaysDate);
}
