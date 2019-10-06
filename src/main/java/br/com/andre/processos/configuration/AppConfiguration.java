package br.com.andre.processos.configuration;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
