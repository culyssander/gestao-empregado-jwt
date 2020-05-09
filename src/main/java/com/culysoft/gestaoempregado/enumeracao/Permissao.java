package com.culysoft.gestaoempregado.enumeracao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permissao {
	
	EMPREGADO_LEITURA("empregado:leitura"),
	EMPREGADO_ESCRITA("empregado:escrita"),
	AUTENTICACAO_LEITURA("autenticacao:leitura"),
	AUTENTICACAO_ESCRITA("autenticacao:escrita");
	
	private String permissao;

}
