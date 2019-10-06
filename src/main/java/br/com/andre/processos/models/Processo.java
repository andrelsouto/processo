package br.com.andre.processos.models;

import br.com.andre.processos.models.enumerations.SituacaoProcessoEnum;
import com.opencsv.bean.CsvBindByPosition;
import org.hibernate.annotations.Where;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Where(clause = "deleted = false")
@Validated
public class Processo extends AbstractEntity {

	@CsvBindByPosition(position = 0)
	@NotNull
	@Size(min = 20, max = 20)
	@Pattern(regexp = "^[0-9]{20}$")
	private String numero;
	@CsvBindByPosition(position = 1)
	@NotNull
	private String nome;
	@Pattern(regexp = "^2[0-9]{3}")
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
