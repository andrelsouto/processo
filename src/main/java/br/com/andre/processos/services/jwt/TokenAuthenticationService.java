package br.com.andre.processos.services.jwt;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.andre.processos.models.Role;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

public class TokenAuthenticationService {
	
	static final long EXPIRATION_TIME = 3600L * 1000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	public static void addAuthetication(HttpServletResponse response, Authentication auth) throws JsonProcessingException {


		Map<String, Object> authorities = new HashMap<>();
		authorities.put("ROLES", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		String JWT = Jwts.builder()
						.setSubject(auth.getName())
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.claim("ROLES", authorities.get("ROLES"))
						.signWith(SignatureAlgorithm.HS512, SECRET)
						.compact();
		
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	public static Authentication getAuthetication(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {

			Claims body = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
			String user = body
							.getSubject();

			List<String> roles = (List<String>) body.get("ROLES");

			List<Role> authorities = roles.stream().collect(ArrayList::new, (roles1, e) -> roles1.add(new Role(e)), ArrayList::addAll);

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities)
					: null;
		}

		return null;
	}

}
