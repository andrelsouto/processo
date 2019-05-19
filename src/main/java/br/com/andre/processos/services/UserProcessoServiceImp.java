package br.com.andre.processos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.andre.processos.models.UserProcesso;
import br.com.andre.processos.repository.UserProcessoRepository;

@Service
public class UserProcessoServiceImp implements UserProcessoService {
	
	@Autowired
	private UserProcessoRepository repository;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserProcesso user = repository.findByEmail(username);

		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
	}

}
