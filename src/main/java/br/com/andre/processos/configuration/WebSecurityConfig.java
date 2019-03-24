package br.com.andre.processos.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.andre.processos.filters.JwtAuthenticationFilter;
import br.com.andre.processos.filters.JwtLoginFilter;
import br.com.andre.processos.services.UserProcessoService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private UserProcessoService uService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.cors()
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.csrf().disable()
			.authorizeRequests().antMatchers("*", "/login").permitAll()
				.and()
			.authorizeRequests()
			.anyRequest().authenticated()
				.and()
				.addFilterBefore(new JwtLoginFilter("/login", authenticationManager ()),
		                UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub

		auth.userDetailsService(uService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(
				Arrays.asList("https://glacial-brushlands-71010.herokuapp.com", "https://processo.herokuapp.com", 
						"http://glacial-brushlands-71010.herokuapp.com", "http://processo.herokuapp.com"));
//		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
		corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "UPDATE", "DELETE", "PUT"));
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addExposedHeader("Authorization");
		corsConfiguration.setAllowCredentials(false);
		corsConfiguration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		return source;
	}
}
