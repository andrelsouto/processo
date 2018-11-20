package br.com.andre.processos.models;

import javax.persistence.Entity;

@Entity
public class Arquivo extends AbstractEntity{
	
	private byte[] file;
	
	public Arquivo() {
		
	}
	
	public Arquivo(byte[] file) {
		
		this.file = file;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

}
