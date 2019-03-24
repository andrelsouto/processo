package br.com.andre.processos.filters;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.andre.processos.models.AccountCredentials;
import br.com.andre.processos.services.jwt.TokenAuthenticationService;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public JwtLoginFilter(String url, AuthenticationManager manager) {
		
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(manager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		
		System.out.println("aqui");
		AccountCredentials credentials = new ObjectMapper()
											.readValue(request.getInputStream(), AccountCredentials.class);
		
		return getAuthenticationManager().authenticate(
											new UsernamePasswordAuthenticationToken(credentials.getUsername(), 
																					credentials.getPassword(), 
																					Collections.emptyList())
										);
		
	}
	
	@Override
	protected void successfulAuthentication(
					HttpServletRequest request,
					HttpServletResponse response,
					FilterChain chain,
					Authentication auth
					) throws JsonProcessingException {
		
		TokenAuthenticationService.addAuthetication(response, auth.getName());
	}

}