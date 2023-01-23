package com.example.prenotazionePalestra.controller;

import com.example.prenotazionePalestra.entity.*;
import com.example.prenotazionePalestra.repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Controller	
@RequestMapping(path="/app/utente") 
public class utenteController {
	@Autowired 
	private UtenteRepository utenteRepository;


	
	@GetMapping("/addUtenteForm")
	public ModelAndView addUtenteForm() {
		ModelAndView mav = new ModelAndView("addUtenteform");
		Utente newUtente = new Utente();
		mav.addObject("utente", newUtente);
		return mav;
	}

	@PostMapping("/saveUtente")
	public String saveUtente(@ModelAttribute Utente u) {
		utenteRepository.save(u);
		return "redirect:/app/utente/all";
	}

	//----------------------------------INDIA
	
	@GetMapping(path="/all")
	public ModelAndView getAllUtenti() {
		ModelAndView mav = new ModelAndView("listaUtenti");
		mav.addObject("utenti", utenteRepository.findAll());
		return mav;
	}
	






	
	//-----------------------------------------------------------
	
	/* 
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@RequestParam String nome, @RequestParam String cognome
			, @RequestParam String email, @RequestParam Integer cert, @RequestParam Integer tipo) {

		Utente n = new Utente();
		n.setNome(nome);
        n.setCognome(cognome);
		n.setEmail(email);
        n.setCertificato(cert);
        n.setTipo(tipo);
		utenteRepository.save(n);
		return "Utente salvato";
	}
	
	//----------------------------------------------

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Utente> getAllUsers() {
		// This returns a JSON or XML with the users
		return utenteRepository.findAll();
	}
	*/
	
}
