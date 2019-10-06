package br.com.andre.processos.models;

import br.com.andre.processos.models.enumerations.SituacaoProcessoEnum;
import com.opencsv.bean.CsvBindByPosition;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Where(clause = "deleted = false")
public class Processo extends AbstractEntity {

	@CsvBindByPosition(position = 0)
	private String numero;
	@CsvBindByPosition(position = 1)
	private String nome;
	private String anoMeta;
	@Column(nullable = false)
	private boolean deleted;
	private SituacaoProcessoEnum situacao;

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

	public String getAnoMeta() {
		return anoMeta;
	}

	public void setAnoMeta(String anoMeta) {
		this.anoMeta = anoMeta;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public SituacaoProcessoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcessoEnum situacao) {
		this.situacao = situacao;
	}
}
