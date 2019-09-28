package br.com.andre.processos.models.enumerations;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SituacaoProcessoEnum {
    NAO_UTILIZAR(0, ""),
    PROCESSO_SENTENCIADO(1, "Processo sentenciado"),
    PROCESSO_NAO_SENTENCIADO(2, "Processo a sentenciar"),
    PROCESSO_SUSPENSO(3, "Processo suspenso");

    private int codigo;
    private String descricao;

    SituacaoProcessoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
