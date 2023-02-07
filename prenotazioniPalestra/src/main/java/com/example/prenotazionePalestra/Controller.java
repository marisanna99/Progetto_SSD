package com.example.prenotazionePalestra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.prenotazionePalestra.entity.Prenotazione;
import com.example.prenotazionePalestra.entity.Utente;
import com.example.prenotazionePalestra.repository.PrenRepository;
import com.example.prenotazionePalestra.repository.UtenteRepository;

@RestController
@RequestMapping(path = "/app")
public class Controller {

	@Autowired
	private PrenRepository prenRepository;

	@Autowired
	private UtenteRepository uRepo;

	@GetMapping("/Home")
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}



	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, Authentication authentication) throws ServletException {
		int r=0;
		String ruolo = authentication.getAuthorities().stream().findFirst().get().toString();
		if(ruolo.equals("ROLE_atleta_role")){
			r=2;
		}else if(ruolo.equals("ROLE_trainer_role")){
			r=1;
		}else if(ruolo.equals("ROLE_admin_role")){
			r=0;
		}
		request.logout();
		ModelAndView mav = new ModelAndView("logout");
		mav.addObject("r", r);
		return mav;
	}

	// ---------------ATLETA-----------------

	@RolesAllowed("atleta_role")
	@GetMapping("/DashboardAtleta")
	public ModelAndView DashboardAtleta(Authentication authentication) {
		// logging(request);
		String username = authentication.getName();
		ModelAndView mav = new ModelAndView("dashAtleta");
		mav.addObject("username", username);
		return mav;

		
	}

	@RolesAllowed("atleta_role")
	@GetMapping("/atleta/addPrenotazioneForm")
	public ModelAndView addPrenotazioneForm() {
		ModelAndView mav = new ModelAndView("addPrenform");
		Prenotazione newPren = new Prenotazione();
		mav.addObject("prenotazione", newPren);
		return mav;
	}

	@RolesAllowed("atleta_role")
	@PostMapping("/atleta/savePrenotazione")
	public ModelAndView savePrenotazione(@ModelAttribute Prenotazione p, Authentication authentication) {
		Integer matricola = p.getMatricola();
		Optional<Utente> u = uRepo.findById(matricola);
		if (u.isEmpty()) {
			ModelAndView mav = new ModelAndView("prenNOTOK");
			return mav;
		} else {

			String username = authentication.getName();
			System.out.println(username);
			Integer matconfronto = uRepo.findMatricola(username);
			System.out.println(matconfronto);
			System.out.println(matricola);
			if (matricola.equals(matconfronto)) {
			
					String sala = p.getSala();
					System.out.println(sala);
					if (sala.equals("Attrezzi") || sala.equals("Zumba")) {
						int oraInserita = p.getOraArrivo();
						System.out.println(oraInserita);
						int minInseriti = p.getMinArrivo();
						System.out.println(minInseriti);
						LocalDateTime todaysDateTime = LocalDateTime.now();
						int min = todaysDateTime.getMinute();
						int hour = todaysDateTime.getHour();
						System.out.println(min);
						System.out.println(hour);
						if((oraInserita > hour) || ((oraInserita==hour)&&(minInseriti>=min))){
							try {
								System.out.println("Controlli matricola, ora e min SUPERATI");
								if(minInseriti==0 || minInseriti==00){
									p.setMinArrivo(00);
								}
								LocalDate todaysDate = LocalDate.now();
								p.setData(todaysDate);
								prenRepository.save(p);
								ModelAndView mav = new ModelAndView("prenOK");
								return mav;
							} catch (Exception e) {
								ModelAndView mav = new ModelAndView("prenNOTOK");
								return mav;
							}
						}else{
							ModelAndView mav = new ModelAndView("prenNOTOK");
							return mav;
						}
						
					}else {
						ModelAndView mav = new ModelAndView("prenNOTOK");
						return mav;
					}
			} else {
				ModelAndView mav = new ModelAndView("prenNOTOK");
				return mav;
			}

		}

	}

	// -------------------TRAINER----------------------

	@RolesAllowed("trainer_role")
	@GetMapping("/DashboardTrainer")
	public ModelAndView DashboardTrainer(Authentication authentication) {
		// logging(request);
		String username = authentication.getName();
		ModelAndView mav = new ModelAndView("dashTrainer");
		mav.addObject("username", username);
		return mav;
	}

	@GetMapping(path = "/trainer/allPren")
	public ModelAndView getAllPrenotazioni() {
		ModelAndView mav = new ModelAndView("listaPrenotazioni");
		mav.addObject("prenotazioni", prenRepository.findAll());
		return mav;
	}

	@GetMapping(path = "/trainer/todayPren")
	public ModelAndView getTodayPren() {
		LocalDate todaysDate = LocalDate.now();
		ModelAndView mav = new ModelAndView("listaPrenotazioni");
		mav.addObject("prenotazioni", prenRepository.findPrenOggi(todaysDate));
		return mav;
	}

	// -------------------ADMIN-----------------------

	@RolesAllowed("admin_role")
	@GetMapping("/DashboardAdmin")
	public ModelAndView DashboardAdmin(Authentication authentication) {
		// logging(request);
		String username = authentication.getName();
		ModelAndView mav = new ModelAndView("dashAdmin");
		mav.addObject("username", username);
		return mav;
	}

	@RolesAllowed("admin_role")
	@GetMapping("/admin/addUtenteForm")
	public ModelAndView addUtenteForm() {
		ModelAndView mav = new ModelAndView("addUtenteform");
		Utente newUtente = new Utente();
		mav.addObject("utente", newUtente);
		return mav;
	}

	@RolesAllowed("admin_role")
	@PostMapping("/admin/saveUtente")
	public ModelAndView saveUtente(@ModelAttribute Utente u) {
		try {
			Random rd = new Random();
			LocalDateTime todaysDate = LocalDateTime.now();
			int sec = todaysDate.getSecond();
			int min = todaysDate.getMinute();
			int hour = todaysDate.getHour();
			int day = todaysDate.getDayOfMonth();
			int month = todaysDate.getMonthValue();
			int year = todaysDate.getYear();
			int unique = (sec + min + day + month + year) * hour;
			int x = (rd.nextInt(900) + 100) * unique; // creo matricola random da 100 a 999
			u.setMatricola(x);
			uRepo.save(u);
			return getAllUtenti();
		} catch (Exception e) {
			ModelAndView mav = new ModelAndView("newUtenteNOTOK");
			return mav;
		}

	}

	@RolesAllowed("admin_role")
	@GetMapping("/admin/allUtenti")
	public ModelAndView getAllUtenti() {
		ModelAndView mav = new ModelAndView("listaUtenti");
		mav.addObject("utenti", uRepo.findAll());
		return mav;
	}

	@RolesAllowed("admin_role")
	@GetMapping("/admin/scegliUtente")
	public ModelAndView scegliUtente() {
		ModelAndView mav = new ModelAndView("eliminaUtente");
		Utente utenteDaEliminare = new Utente();
		mav.addObject("utente", utenteDaEliminare);
		return mav;
	}

	@RolesAllowed("admin_role")
	@PostMapping("/admin/deleteUtente")
	public ModelAndView eliminaUtente(@ModelAttribute Utente ut) {
		System.out.println("Entrato nella funzione");
		int matricola = ut.getMatricola();
		Optional<Utente> u = uRepo.findById(matricola);
		if(u.isEmpty()){
			ModelAndView mav = new ModelAndView("utenteNonEsistente");
			return mav;
		}else{
			try {
				System.out.println("Entrato qui");
				uRepo.deleteById(matricola);
				ModelAndView mav = new ModelAndView("utenteEliminato");
				return mav;
			} catch (Exception e) {
				ModelAndView mav = new ModelAndView("utenteNonEsistente");
				return mav;
			}
		}
		
	}
	
}