package com.binha.exerciciossb.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeiroController {

	@RequestMapping(method = RequestMethod.GET, path = { "/ola" })
	public String ola() {
		return "<h1 style='color:darkblue'>Olá Spring Boot da Binha!</h1>";
	}

}
