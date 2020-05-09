package com.culysoft.gestaoempregado.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.culysoft.gestaoempregado.model.Autenticacao;
import com.culysoft.gestaoempregado.service.AutenticacaoService;

@RestController
@RequestMapping("gestaoempregado/api/v1/admin/autenticacoes")
public class AutenticacaoController {
	
	@Autowired private AutenticacaoService autenticacaoService;

	@GetMapping
	public List<Autenticacao> buscarTodos(){
		return autenticacaoService.buscarTodos();
	}
	@GetMapping("{codigo}")
	public ResponseEntity<Autenticacao> buscarAutenticacaoPeloCodigo(@PathVariable Long codigo) {
		return autenticacaoService.buscarAutenticacaoPeloCodigo(codigo);
	}
	
	@PostMapping
	public void salvarAutenticacao(@Valid @RequestBody Autenticacao autenticacao) {
		autenticacaoService.salvarAutenticacao(autenticacao);
	}
	
	@PutMapping
	public void actualizarAutenticacao(@Valid @RequestBody Autenticacao autenticacao) {
		autenticacaoService.actualizarAutenticacao(autenticacao);
	}
	
	@DeleteMapping("{codigo}")
	public ResponseEntity<String> deleteByCodigo(@PathVariable Long codigo) {
		return autenticacaoService.deleteByCodigo(codigo);
	}
	
}
