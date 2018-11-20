package br.com.andre.processos.models;

import java.util.UUID;

public class ProcessoRelatorio {

	private Processo processo;
	
	public ProcessoRelatorio(Processo processo) {
		
		this.processo = processo;
	}
	
	public UUID getId() {
		return this.processo.getId();
	}
	
	public String getNumero() {
		return this.processo.getNumero();
	}
}
