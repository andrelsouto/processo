package br.com.andre.processos.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.opencsv.bean.CsvBindByPosition;

@Entity
public class Processo extends AbstractEntity {

	@CsvBindByPosition(position = 0)
	@Column(unique = true)
	private String numero;
	@CsvBindByPosition(position = 1)
	private String nome;
	private boolean setenciado;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isSetenciado() {
		return setenciado;
	}

	public void setSetenciado(boolean setenciado) {
		this.setenciado = setenciado;
	}

}
