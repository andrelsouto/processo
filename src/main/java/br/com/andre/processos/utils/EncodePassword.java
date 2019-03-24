package br.com.andre.processos.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePassword {
	
	public static String encodePassword(String args) {
		
		return new BCryptPasswordEncoder().encode(args);
	}

}
