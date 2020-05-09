package com.culysoft.gestaoempregado.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.culysoft.gestaoempregado.enumeracao.Permissao;
import com.culysoft.gestaoempregado.enumeracao.Roles;
import com.culysoft.gestaoempregado.exception.NegocioException;
import com.culysoft.gestaoempregado.filter.JwtFilter;
import com.culysoft.gestaoempregado.model.Autenticacao;
import com.culysoft.gestaoempregado.model.AutenticacaoUserDetails;
import com.culysoft.gestaoempregado.repository.AutenticacaoRepository;
import com.culysoft.gestaoempregado.service.AutenticacaoService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired private AutenticacaoRepository autenticacaoRepository;
	@Autowired private AutenticacaoService autenticacaoService;
	@Autowired private JwtFilter jwtFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/login").permitAll()
    		.antMatchers(HttpMethod.GET, "/gestaoempregado/api/v1/padrao/empregado").hasRole(Roles.PADRAO.name())
    		.antMatchers(HttpMethod.POST, "/gestaoempregado/api/v1/admin/**").hasAuthority(Permissao.AUTENTICACAO_ESCRITA.getPermissao())
    		.antMatchers(HttpMethod.PUT, "/gestaoempregado/api/v1/admin/**").hasAuthority(Permissao.AUTENTICACAO_ESCRITA.getPermissao())
    		.antMatchers(HttpMethod.DELETE, "/gestaoempregado/api/v1/admin/**").hasAuthority(Permissao.AUTENTICACAO_ESCRITA.getPermissao())
    		.antMatchers(HttpMethod.GET, "/gestaoempregado/api/v1/admin/**").hasAnyRole(Roles.ADMIN.name(), Roles.ADMINLEITURA.name())
			.anyRequest()
			.authenticated();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService((utilizador) -> {
				Autenticacao autenticacao = autenticacaoRepository.findByUtilizador(utilizador);
				if (autenticacao == null)
					throw new NegocioException(utilizador);
				
				autenticacaoService.salvarUltimaAutenticacao(autenticacao);
				return new AutenticacaoUserDetails(autenticacao);
			}
		).passwordEncoder(passwordEncoder());
	}
}
