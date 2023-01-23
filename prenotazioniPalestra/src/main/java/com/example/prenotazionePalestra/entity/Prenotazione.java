package com.example.prenotazionePalestra.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // This tells Hibernate to make a table out of this class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione{
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idPren;
	private Integer idUtente;

    @NotNull
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String sala;

    @NotNull
	@Positive
	private Integer oraArrivo;
	private Integer minArrivo;

}