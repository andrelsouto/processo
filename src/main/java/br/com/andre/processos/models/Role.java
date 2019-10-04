package br.com.andre.processos.models;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role extends AbstractEntity implements GrantedAuthority {
	
	private static final long serialVersionUID = 2042514825923351054L;
	
	private String role;
	
	public Role() {}
	
	public Role(String role) {
		this.role = role;
		
	}
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}
}
