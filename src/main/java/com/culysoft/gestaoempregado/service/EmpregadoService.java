package com.culysoft.gestaoempregado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.culysoft.gestaoempregado.exception.NegocioException;
import com.culysoft.gestaoempregado.model.Empregado;
import com.culysoft.gestaoempregado.repository.AutenticacaoRepository;
import com.culysoft.gestaoempregado.repository.EmpregadoRepository;

@Service
public class EmpregadoService {
	
	@Autowired private EmpregadoRepository empregadoRepository;
	@Autowired private AutenticacaoRepository autenticacaoRepository;
	
	public List<Empregado> buscarTodos(){
		List<Empregado> empregados = empregadoRepository.findAll();
		
		if(empregados == null || empregados.isEmpty()) {
			throw new NegocioException("Lista dos empregados vazia");
		}
		return empregados;
	}
	
	public ResponseEntity<Empregado> buscarEmpregadoPeloCodigo(Long codigo) {
		Empregado empregado = empregadoRepository.findById(codigo)
				.orElseThrow(() -> new NegocioException("Empregado " + codigo + " não existe"));
		return new ResponseEntity<Empregado>(empregado, HttpStatus.OK);
	}
	
	public void salvarEmpregado(Empregado empregado) {
		validacao(empregado);
		empregadoRepository.save(empregado);
	}
	
	public void actualizarEmpregado(Empregado empregado) {
		if(empregadoRepository.existsById(empregado.getCodigo())) {
			validacao(empregado);
			empregadoRepository.save(empregado);
		}
		throw new NegocioException("Empregado " + empregado + " não existe");
	}
	
	public ResponseEntity<String> deleteByCodigo(Long codigo) {
		try {
			empregadoRepository.deleteById(codigo);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public Empregado buscarEmpregadoPelaCodigoDaAutenticacao(String utilizador) {
		Empregado empregado = empregadoRepository.findByAutenticacaoUtilizador(utilizador);
		
		if(empregado == null) {
			throw new NegocioException("Não estas cadastrados como empregado. Por favor, contacte o admin.");
		}
		return empregado;
	}
	
	public void validacao(Empregado empregado) {
		if(empregado.getAutenticacao() == null) {
			throw new NegocioException("Autenticação é obrigatório");
		}
		if(!existeAutenticacao(empregado.getAutenticacao().getCodigo())) {
			
			throw new NegocioException("Autenticação "+empregado.getAutenticacao().getCodigo()+" Invalida");
		}
	}
	
	private boolean existeAutenticacao(Long codigo) {
		if(autenticacaoRepository.existsById(codigo)) {
			return true;
		}
		return false;
	}
}
