package br.com.andre.processos.models;

import br.com.andre.processos.models.enumerations.SituacaoProcessoEnum;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ProcessoRelatorio {

	private Processo processo;
	private byte[] sent;

	public ProcessoRelatorio(Processo processo, byte[] sent) {
		
		this.processo = processo;
		this.sent = sent;
	}
	
	public String getNumero() {
		return this.processo.getNumero();
	}

	public String getNome() {
		return this.processo.getNome();
	}

	public String getAno() {
		return this.processo.getAnoMeta();
	}

	public InputStream getSent() {
		return new ByteArrayInputStream(this.sent);
	}

	public boolean getSentenciado() {
		return this.processo.getSituacao().equals(SituacaoProcessoEnum.PROCESSO_SENTENCIADO);
	}
}
