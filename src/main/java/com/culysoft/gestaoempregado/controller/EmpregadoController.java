package com.culysoft.gestaoempregado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.culysoft.gestaoempregado.converter.AutenticacaoPropertyEditors;
import com.culysoft.gestaoempregado.model.Autenticacao;
import com.culysoft.gestaoempregado.model.Empregado;
import com.culysoft.gestaoempregado.service.EmpregadoService;

@RestController
@RequestMapping("gestaoempregado/api/v1/admin/empregados")
public class EmpregadoController {
	
	@Autowired private EmpregadoService empregadoService;
	@Autowired private AutenticacaoPropertyEditors autenticacaoPropertyEditors;
	
	@GetMapping
	public List<Empregado> buscarTodos(){
		return empregadoService.buscarTodos();
	}
	
	@GetMapping("{codigo}")
	public ResponseEntity<Empregado> buscarEmpregadoPeloCodigo(@PathVariable Long codigo) {
		return empregadoService.buscarEmpregadoPeloCodigo(codigo);
	}
	
	@PostMapping
	public void salvarEmpregado(@RequestBody Empregado empregado) {
		empregadoService.salvarEmpregado(empregado);
	}
	
	@PutMapping
	public void actualizarEmpregado(@RequestBody Empregado empregado) {
		empregadoService.actualizarEmpregado(empregado);
	}
	
	@DeleteMapping("{codigo}")
	public ResponseEntity<String> deleteByCodigo(Long codigo) {
		return empregadoService.deleteByCodigo(codigo);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Autenticacao.class, autenticacaoPropertyEditors);
	}
}
