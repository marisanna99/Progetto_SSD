package com.example.prenotazionePalestra.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.prenotazionePalestra.entity.Prenotazione;

public interface PrenRepository extends CrudRepository<Prenotazione, Integer> {

}