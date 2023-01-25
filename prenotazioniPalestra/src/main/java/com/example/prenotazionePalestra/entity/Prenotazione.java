package com.example.prenotazionePalestra.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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