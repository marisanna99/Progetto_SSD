package com.example.prenotazionePalestra.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.prenotazionePalestra.entity.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Integer> {

}