package com.example.prenotazionePalestra.controller;

import com.example.prenotazionePalestra.entity.*;
import com.example.prenotazionePalestra.repository.*;

import jakarta.annotation.security.RolesAllowed;

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
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.time.LocalDate;

@Controller	
@RequestMapping(path="/app/prenotazioni") 
public class prenController {
	
	@Autowired
	private PrenRepository prenRepository;

	@Autowired
	private UtenteRepository uRepo;

	@RolesAllowed("atleta_role")
	@GetMapping("/addPrenotazioneForm")
	public ModelAndView addPrenotazioneForm() {
		ModelAndView mav = new ModelAndView("addPrenform");
		Prenotazione newPren = new Prenotazione();
		mav.addObject("prenotazione", newPren);
		return mav;
	}

	@PostMapping("/savePrenotazione")
	public String savePrenotazione(@ModelAttribute Prenotazione p) {
		Integer matricola = p.getMatricola();
		Optional<Utente> u = uRepo.findById(matricola);
		if (u.isEmpty()){
			return "redirect:/app/prenotazioni/prenNOTOK";
		}else{
			try {
				LocalDate todaysDate = LocalDate.now();
				p.setData(todaysDate);
				prenRepository.save(p);
				return "redirect:/app/prenotazioni/prenOK";
			} catch (Exception e) {
				// TODO: handle exception
				return "redirect:/app/prenotazioni/prenNOTOK";
			}
		}
		
		
	}


	@GetMapping(path="/prenOK")
	public ModelAndView mostraMsgOK() {
		ModelAndView mav = new ModelAndView("prenOK");
		return mav;	
	}

	@GetMapping(path="/prenNOTOK")
	public ModelAndView mostraMsgNOTOK() {
		ModelAndView mav = new ModelAndView("prenNOTOK");
		return mav;	
	}

	@RolesAllowed("trainer_role")
	@GetMapping(path="/all")
	public ModelAndView getAllPrenotazioni() {
		ModelAndView mav = new ModelAndView("listaPrenotazioni");
		mav.addObject("prenotazioni", prenRepository.findAll());
		return mav;
	}






	//---------------------------------------------------------------
	/*
	@PostMapping(path="/add/{idU}") // Map ONLY POST Requests
	public @ResponseBody String addNewPren (@PathVariable("idU")  Integer idU, @RequestParam String sala
				, @RequestParam Integer ora, @RequestParam Integer min) {

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
*/
	
}
