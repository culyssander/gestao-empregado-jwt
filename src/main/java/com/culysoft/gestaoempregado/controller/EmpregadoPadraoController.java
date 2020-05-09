package com.culysoft.gestaoempregado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.culysoft.gestaoempregado.model.Empregado;
import com.culysoft.gestaoempregado.service.EmpregadoService;

@RestController
@RequestMapping("gestaoempregado/api/v1/padrao/empregado")
public class EmpregadoPadraoController {
	
	@Autowired private EmpregadoService empregadoService;
	
	@GetMapping
	public Empregado buscarEmpregadoAutenticado() {
		String utilizador = SecurityContextHolder.getContext().getAuthentication().getName();
		return empregadoService.buscarEmpregadoPelaCodigoDaAutenticacao(utilizador);
	}	
	
}
