package br.com.andre.processos.configuration;

import br.com.andre.processos.filters.JwtAuthenticationFilter;
import br.com.andre.processos.filters.JwtLoginFilter;
import br.com.andre.processos.services.UserProcessoService;
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

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	private UserProcessoService uService;
	private CorsPropertiesConfiguration corsPropertiesConfiguration;

	@Autowired
	public WebSecurityConfig(UserProcessoService uService, CorsPropertiesConfiguration corsPropertiesConfiguration) {
		this.uService = uService;
		this.corsPropertiesConfiguration = corsPropertiesConfiguration;
	}

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
				.authorizeRequests().antMatchers("*", "/processo/token").hasRole("SUPER_ADMIN")
				.and()
				.authorizeRequests().antMatchers("*", "/processo/sentenciar/**").permitAll()
				.antMatchers("/processo/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(new JwtLoginFilter("/login", authenticationManager()),
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
		corsConfiguration.setAllowedOrigins(corsPropertiesConfiguration.getHeaders());
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
