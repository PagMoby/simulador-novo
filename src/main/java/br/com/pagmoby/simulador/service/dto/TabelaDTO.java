package br.com.pagmoby.simulador.service.dto;

import br.com.pagmoby.simulador.domain.enumeration.Operacao;

import java.math.BigDecimal;

public class TabelaDTO {

    private Operacao operacao;
    private String valorReceber;
    private String ValorDaVenda;

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public String getValorReceber() {
        return valorReceber;
    }

    public void setValorReceber(String valorReceber) {
        this.valorReceber = valorReceber;
    }

    public String getValorDaVenda() {
        return ValorDaVenda;
    }

    public void setValorDaVenda(String valorDaVenda) {
        ValorDaVenda = valorDaVenda;
    }
}
