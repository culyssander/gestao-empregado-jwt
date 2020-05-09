package com.culysoft.gestaoempregado.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.culysoft.gestaoempregado.exception.NegocioException;
import com.culysoft.gestaoempregado.model.AuthRequest;
import com.culysoft.gestaoempregado.util.JwtUtil;

@RestController
@RequestMapping
public class HomeController {
	
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    private Authentication authentication;

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUtilizador(), authRequest.getPalavraPasse())
            );
        } catch (Exception ex) {
            throw new NegocioException("Utilizador/Palavra Passe invalida.");
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return jwtUtil.generateToken(authorities, authRequest.getUtilizador());
    }

}
