package com.example.prenotazionePalestra.entity;

import java.time.LocalDate;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione{
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idPren;

	@NotNull
	private Integer matricola;

    @NotNull
	@Pattern(regexp = "[a-zA-Z]+")
	private String sala;


    @NotNull
	@Positive
	@Digits(integer = 2, fraction = 0)
	@Min(00)
	@Max(23)
	private Integer oraArrivo;

	@NotNull
	@Positive
	@Digits(integer = 2, fraction = 0)
	@Min(00)
	@Max(59)
	private Integer minArrivo;
	
	@NotNull
	private LocalDate data;

}