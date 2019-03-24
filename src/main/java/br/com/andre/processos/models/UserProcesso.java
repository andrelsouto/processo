package br.com.andre.processos.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.andre.processos.utils.EncodePassword;

@Entity
public class UserProcesso extends AbstractEntity implements UserDetails {
	
	private static final long serialVersionUID = -389154091919291374L;
	
	@Column(unique = true)
	private String email;
	private String password;
	private String nome;
	@OneToMany(fetch=FetchType.EAGER)
	private  List<Role> roles = new ArrayList<>();
	
	public UserProcesso() { }
	
	public UserProcesso (String email, String password, String nome, List<Role> roles) {
		
		this.email = email;
		this.password = EncodePassword.encodePassword(password);
		this.nome = nome;
		this.roles = roles;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
