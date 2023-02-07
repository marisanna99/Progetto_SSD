package com.example.prenotazionePalestra.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.prenotazionePalestra.entity.Prenotazione;

public interface PrenRepository extends CrudRepository<Prenotazione, Integer> {

    @Query(value="select * from prenotazione where data like %?1", nativeQuery=true)
    Iterable<Prenotazione> findPrenOggi(LocalDate todaysDate);

}