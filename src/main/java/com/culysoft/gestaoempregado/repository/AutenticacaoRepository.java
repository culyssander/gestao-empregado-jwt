package com.culysoft.gestaoempregado.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.culysoft.gestaoempregado.model.Autenticacao;

@Repository
public interface AutenticacaoRepository extends CrudRepository<Autenticacao, Long> {
	List<Autenticacao> findAll();
	Autenticacao findByUtilizador(String utilizador);
}
