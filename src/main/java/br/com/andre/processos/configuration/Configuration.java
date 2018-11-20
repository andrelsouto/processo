package br.com.andre.processos.configuration;

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
				.allowedOrigins("http://192.168.15.15", "http://192.168.15.15:8082")
				.allowedHeaders("Access-Control-Allow-Origin", "Content-Disposition")
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
