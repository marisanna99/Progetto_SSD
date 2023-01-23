package com.example.prenotazionePalestra.controller;

import com.example.prenotazionePalestra.entity.*;
import com.example.prenotazionePalestra.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller	
@RequestMapping(path="/app/prenotazioni") 
public class prenController {
	
	@Autowired
	private PrenRepository prenRepository;


	@PostMapping(path="/add/{idU}") // Map ONLY POST Requests
	public @ResponseBody String addNewPren (@PathVariable("idU")  Integer idU, @RequestParam String sala
				, @RequestParam Integer ora, @RequestParam Integer min) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Prenotazione p = new Prenotazione();
		p.setIdUtente(idU);
		p.setSala(sala);
        p.setOraArrivo(ora);
		p.setMinArrivo(min);
		prenRepository.save(p);
		return "Prenotazione salvata";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Prenotazione> getAllPrens() {
		// This returns a JSON or XML with the users
		return prenRepository.findAll();
	}
}
