package br.com.pagmoby.simulador.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import br.com.pagmoby.simulador.domain.enumeration.Operacao;

/**
 * A DTO for the {@link br.com.pagmoby.simulador.domain.Juros} entity.
 */
public class JurosDTO implements Serializable {
    
    private Long id;

    private BigDecimal juros;

    private Operacao operacao;


    private Long planoId;

    private String planoNome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public Long getPlanoId() {
        return planoId;
    }

    public void setPlanoId(Long planoId) {
        this.planoId = planoId;
    }

    public String getPlanoNome() {
        return planoNome;
    }

    public void setPlanoNome(String planoNome) {
        this.planoNome = planoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JurosDTO)) {
            return false;
        }

        return id != null && id.equals(((JurosDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JurosDTO{" +
            "id=" + getId() +
            ", juros=" + getJuros() +
            ", operacao='" + getOperacao() + "'" +
            ", planoId=" + getPlanoId() +
            ", planoNome='" + getPlanoNome() + "'" +
            "}";
    }
}
