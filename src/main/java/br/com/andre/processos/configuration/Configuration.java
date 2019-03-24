package br.com.andre.processos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.andre.processos.utils.PDFReport;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@Bean
	public PDFReport report() {
		
		return new PDFReport();
	}
}
