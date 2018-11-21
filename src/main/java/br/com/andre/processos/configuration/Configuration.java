package br.com.andre.processos.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.andre.processos.utils.PDFReport;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				
				registry.addMapping("/**")
				.allowedOrigins("https://processo.herokuapp.com")
				.allowedOrigins("https://glacial-brushlands-71010.herokuapp.com")
				.allowedHeaders("Access-Control-Allow-Origin", "Content-Disposition", "User-Agent")
				.allowedMethods("POST", "DELETE", "GET", "PATCH", "PUT", "UPDATE")
				.allowCredentials(false).maxAge(3600);
			}
		}; 
	}
	@Bean
	public PDFReport report() {
		
		return new PDFReport();
	}
}
