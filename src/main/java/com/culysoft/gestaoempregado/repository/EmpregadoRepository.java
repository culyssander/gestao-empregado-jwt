package com.culysoft.gestaoempregado.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.culysoft.gestaoempregado.model.Empregado;
import java.util.Optional;

@Repository
public interface EmpregadoRepository extends CrudRepository<Empregado, Long> {
	List<Empregado> findAll();
	Optional<Empregado> findByEmail(String email);
	Optional<Empregado> findByTelefone(String telefone);
	Empregado findByAutenticacaoUtilizador(String utilizador);
}
