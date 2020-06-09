package br.com.pagmoby.simulador.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.pagmoby.simulador.domain.enumeration.Operacao;

/**
 * A Juros.
 */
@Entity
@Table(name = "juros")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Juros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "juros", precision = 21, scale = 2)
    private BigDecimal juros;

    @Enumerated(EnumType.STRING)
    @Column(name = "operacao")
    private Operacao operacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "juros", allowSetters = true)
    private Plano plano;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public Juros juros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public Juros operacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public Plano getPlano() {
        return plano;
    }

    public Juros plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Juros)) {
            return false;
        }
        return id != null && id.equals(((Juros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Juros{" +
            "id=" + getId() +
            ", juros=" + getJuros() +
            ", operacao='" + getOperacao() + "'" +
            "}";
    }
}
