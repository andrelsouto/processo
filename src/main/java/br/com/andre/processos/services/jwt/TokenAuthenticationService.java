package br.com.andre.processos.services.jwt;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	static final long EXPIRATION_TIME = 3600L * 1000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	public static void addAuthetication(HttpServletResponse response, String username) throws JsonProcessingException {
		
		String JWT = Jwts.builder()
						.setSubject(username)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, SECRET)
						.compact();
		
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	public static Authentication getAuthetication(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			
			String user = Jwts.parser()
							.setSigningKey(SECRET)
							.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
							.getBody()
							.getSubject();
			
			if (user != null) {
				
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		
		return null;
	}

}