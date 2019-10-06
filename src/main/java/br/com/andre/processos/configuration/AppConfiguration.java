package br.com.andre.processos.configuration;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class AppConfiguration {
	
	@Bean
	public CacheManager cacheManager() {
		
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		List<ConcurrentMapCache> caches = new ArrayList<>();
		caches.add(new ConcurrentMapCache("processos"));
		caches.add(new ConcurrentMapCache("processosSent"));
		caches.add(new ConcurrentMapCache("processosNSent"));
		cacheManager.setCaches(caches);
		return cacheManager;
	}

	@Bean
	public QRCodeWriter qRCodeWriter() {

		return new QRCodeWriter();
	}

	@Bean
	@Profile("dev")
	CorsPropertiesConfiguration getCors() {

		CorsPropertiesConfiguration cors = new CorsPropertiesConfiguration();
		List<String> allowedCors = new ArrayList<>();
		allowedCors.add("http://localhost:4200");
		allowedCors.add("http://localhost:8080");
		cors.setHeaders(allowedCors);
		return cors;
	}
}
