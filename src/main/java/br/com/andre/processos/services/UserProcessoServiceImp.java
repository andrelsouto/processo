package br.com.andre.processos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.andre.processos.models.UserProcesso;
import br.com.andre.processos.repository.UserProcessoRepository;

@Service
public class UserProcessoServiceImp implements UserProcessoService {
	
	@Autowired
	private UserProcessoRepository repository;
	
	@Override
	public UserProcesso loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repository.findByEmail(username);
	}

}
