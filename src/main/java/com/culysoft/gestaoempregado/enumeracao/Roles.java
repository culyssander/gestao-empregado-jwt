package com.culysoft.gestaoempregado.enumeracao;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {
	
	ADMIN(Sets.newHashSet(Permissao.AUTENTICACAO_LEITURA, Permissao.AUTENTICACAO_ESCRITA, Permissao.EMPREGADO_LEITURA, Permissao.EMPREGADO_ESCRITA)),
	ADMINLEITURA(Sets.newHashSet(Permissao.AUTENTICACAO_LEITURA, Permissao.EMPREGADO_LEITURA)),
	PADRAO(Sets.newHashSet());
	
	private final Set<Permissao> permissoes;
	
	public Set<SimpleGrantedAuthority> getSimpleGrantedAuthority() {
		Set<SimpleGrantedAuthority> permissoes = getPermissoes().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermissao()))
				.collect(Collectors.toSet());
		
		permissoes.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		return permissoes;
	}
}
