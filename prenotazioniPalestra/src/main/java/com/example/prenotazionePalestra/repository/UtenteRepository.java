package com.example.prenotazionePalestra.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

import com.example.prenotazionePalestra.entity.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Integer> {

    @Query(value="select matricola from utente where username like %?1", nativeQuery=true)
    Integer findMatricola(String username);

}