package com.binha.exerciciossb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calc")
public class CalculadoraController {

	@GetMapping("/somar/{a}/{b}")
	public double somar(@PathVariable double a, @PathVariable double b) {
		return a + b;
	}
	
	@GetMapping("/subtrair")
	public double subtrair(
			@RequestParam(name = "a", defaultValue = "0") double a, 
			@RequestParam(name = "b", defaultValue = "0") double b) {
		return a - b;
	}
}
