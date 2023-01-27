package com.example.prenotazionePalestra.controller;

import com.example.prenotazionePalestra.entity.*;
import com.example.prenotazionePalestra.repository.*;

/*
import jakarta.annotation.security.RolesAllowed;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.validation.constraints.NotNull;
*/
import javax.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.lang.Math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.admin.client.Keycloak;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
//import javax.ws.rs.BadRequestException;

@Controller	
@RequestMapping(path="/app") 
public class utenteController {

	@Autowired 
	private UtenteRepository utenteRepository;


	@RolesAllowed("admin_role")
	@GetMapping("/utente/addUtenteForm")
	public ModelAndView addUtenteForm() {
		ModelAndView mav = new ModelAndView("addUtenteform");
		Utente newUtente = new Utente();
		mav.addObject("utente", newUtente);
		return mav;
	}

	@RolesAllowed("admin_role")
	@PostMapping("/utente/saveUtente")
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
			int unique= (sec+min+day+month+year)*hour;
			int x = (rd.nextInt(900) + 100) * unique; //creo matricola random da 100 a 999
			u.setMatricola(x);
			utenteRepository.save(u);
			//return "redirect:/app/utente/all";
			ModelAndView mav = new ModelAndView("listaUtenti");
			mav.addObject("utenti", utenteRepository.findAll());
			return mav;
		} catch (Exception e) {
			//return "redirect:/app/utente/newUtenteNOTOK";
			ModelAndView mav = new ModelAndView("newUtenteNOTOK");
			return mav;	
		}
		
	}

	/*
	@GetMapping(path="/utente/newUtenteNOTOK")
	public ModelAndView userNOTOK() {
		ModelAndView mav = new ModelAndView("newUtenteNOTOK");
		return mav;	
	}
	*/

	/*
	@GetMapping("/Dashboard")
    public ModelAndView Dashboard(Authentication authentication) {
        //logging(request);

		ModelAndView mav = new ModelAndView("dashboard");
        int r = 0;
        String role = authentication.getAuthorities().stream().findFirst().get().toString();
        if (role.equals("ROLE_admin_role")) {
            r = 0;
        } else if (role.equals("ROLE_trainer_role")) {
            r = 1;
        } else if (role.equals("ROLE_atleta_role")){
			r = 2;
		}
		//System.out.println(role);
        mav.addObject("r", r);
        return mav;

    }*/
	@GetMapping("/Dashboard")
	public String Dashboard(HttpServletRequest request, Authentication authentication, Model model) {
       // logging(request);
        int r = 0;
        String role = authentication.getAuthorities().stream().findFirst().get().toString();
        if (role.equals("ROLE_admin_role")) {
            r = 0;
        } else if (role.equals("ROLE_trainer_role")) {
            r = 1;
        } else if (role.equals("ROLE_atleta_role")){
			r = 2;
		}
        model.addAttribute("r", r);
        return "dashboard";

    }

	//----------------------------------INDIA
	/* 
	@RolesAllowed("admin_role")
	@GetMapping(path="/utente/all")
	public ModelAndView getAllUtenti() {
		ModelAndView mav = new ModelAndView("listaUtenti");
		mav.addObject("utenti", utenteRepository.findAll());
		return mav;
	}
	*/



/* 
	@GetMapping(path = "/all")
	public String getAllUtenti(Model model) {
		configCommonAttributes(model);
		model.addAttribute("utenti", utenteRepository.findAll());
		return "listaUtenti";
	}

	private void configCommonAttributes(Model model) {
		model.addAttribute("name", getKeycloakSecurityContext().getIdToken().getGivenName());
	}

	private KeycloakSecurityContext getKeycloakSecurityContext() {
		return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
	}
*/
	
/* 
	@GetMapping("/Dashboard")
    public String Dashboard(HttpServletRequest request, Authentication authentication, Model model) {
        //logging(request);
        int r = 0;
        String role = authentication.getAuthorities().stream().findFirst().get().toString();
        if (role.equals("ROLE_admin_role")) {
            r = 0;
        } else if (role.equals("ROLE_trainer_role")) {
            r = 1;
        } else if (role.equals("ROLE_atleta_role")){
			r = 2;
		}
        model.addAttribute("r", r);
        return "dashboard";

    }
*/
	@GetMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token, HttpServletRequest request) throws ServletException {
        //logging(request);
        request.logout();
        return "logout";
    }




	/* 
	@PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@NotNull @RequestBody LoginRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
            LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }

    }
*/






	
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
