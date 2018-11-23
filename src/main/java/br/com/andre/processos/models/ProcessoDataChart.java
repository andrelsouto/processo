package br.com.andre.processos.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProcessoDataChart {

	private int sents;
	@Column(name = "nsents")
	private int nSents;
	@Id
	private String anoMeta;

	public int getSents() {
		return sents;
	}

	public void setSents(int sents) {
		this.sents = sents;
	}

	public int getnSents() {
		return nSents;
	}

	public void setnSents(int nSents) {
		this.nSents = nSents;
	}

	public String getAnoMeta() {
		return anoMeta;
	}

	public void setAnoMeta(String anoMeta) {
		this.anoMeta = anoMeta;
	}

}
