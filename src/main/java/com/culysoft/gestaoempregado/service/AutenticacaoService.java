package com.culysoft.gestaoempregado.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.culysoft.gestaoempregado.config.SecurityConfig;
import com.culysoft.gestaoempregado.exception.NegocioException;
import com.culysoft.gestaoempregado.model.Autenticacao;
import com.culysoft.gestaoempregado.repository.AutenticacaoRepository;

@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired private AutenticacaoRepository autenticacaoRepository;
	@Autowired private SecurityConfig securityConfig;

	public List<Autenticacao> buscarTodos(){
		List<Autenticacao> autenticacoes = autenticacaoRepository.findAll();
		
		if(autenticacoes == null || autenticacoes.isEmpty()) {
			throw new NegocioException("Lista dos empregados vazia");
		}
		return autenticacoes;
	}
	
	public ResponseEntity<Autenticacao> buscarAutenticacaoPeloCodigo(Long codigo) {
		Autenticacao autenticacao = autenticacaoRepository.findById(codigo)
				.orElseThrow(() -> new NegocioException("Autenticacao " + codigo + " não existe"));
		return new ResponseEntity<Autenticacao>(autenticacao, HttpStatus.OK);
	}
	
	public void salvarAutenticacao(Autenticacao autenticacao) {
		passwordEncode(autenticacao);
		autenticacaoRepository.save(autenticacao);
	}
	
	public ResponseEntity<Autenticacao> actualizarAutenticacao(Autenticacao autenticacao) {
		if(autenticacaoRepository.existsById(autenticacao.getCodigo())) {
			passwordEncode(autenticacao);
			autenticacaoRepository.save(autenticacao);
			return new ResponseEntity<Autenticacao>(HttpStatus.OK);
		}
		throw new NegocioException("Empregado " + autenticacao + " não existe");
	}
	
	public ResponseEntity<String> deleteByCodigo(Long codigo) {
		try {
			autenticacaoRepository.deleteById(codigo);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	

	private void passwordEncode(Autenticacao autenticacao) {
		autenticacao.setPalavraPasse(securityConfig.passwordEncoder().encode(autenticacao.getPalavraPasse()));
	}
	
	public void salvarUltimaAutenticacao(Autenticacao autenticacao) {
		autenticacao.setUltimaAutenticacao(LocalDateTime.now());
		autenticacaoRepository.save(autenticacao);
	}

	@Override
	public UserDetails loadUserByUsername(String utilizador) throws UsernameNotFoundException {
		Autenticacao autenticacao = autenticacaoRepository.findByUtilizador(utilizador);
		if (autenticacao == null)
			throw new NegocioException(utilizador);
		
		salvarUltimaAutenticacao(autenticacao);
		return new User(autenticacao.getUtilizador(), autenticacao.getPalavraPasse(), autenticacao.getPerfil().getSimpleGrantedAuthority());
	}
}
