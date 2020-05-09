package com.culysoft.gestaoempregado.converter;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.culysoft.gestaoempregado.exception.NegocioException;
import com.culysoft.gestaoempregado.model.Autenticacao;
import com.culysoft.gestaoempregado.repository.AutenticacaoRepository;

@Component
public class AutenticacaoPropertyEditors extends PropertyEditorSupport{
	@Autowired private AutenticacaoRepository autenticacaoRepository;
	
	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		long id = Long.parseLong(arg0);
		Autenticacao autenticacao = autenticacaoRepository.findById(id)
				.orElseThrow(()-> new NegocioException("Autenticação " + id + " não existe."));
		setValue(autenticacao);
	}

}