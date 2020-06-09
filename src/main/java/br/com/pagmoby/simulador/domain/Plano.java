package br.com.pagmoby.simulador.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Plano.
 */
@Entity
@Table(name = "plano")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "plano")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Juros> juros = new HashSet<>();

    @OneToMany(mappedBy = "plano")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Seller> sellers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Plano nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Juros> getJuros() {
        return juros;
    }

    public Plano juros(Set<Juros> juros) {
        this.juros = juros;
        return this;
    }

    public Plano addJuros(Juros juros) {
        this.juros.add(juros);
        juros.setPlano(this);
        return this;
    }

    public Plano removeJuros(Juros juros) {
        this.juros.remove(juros);
        juros.setPlano(null);
        return this;
    }

    public void setJuros(Set<Juros> juros) {
        this.juros = juros;
    }

    public Set<Seller> getSellers() {
        return sellers;
    }

    public Plano sellers(Set<Seller> sellers) {
        this.sellers = sellers;
        return this;
    }

    public Plano addSeller(Seller seller) {
        this.sellers.add(seller);
        seller.setPlano(this);
        return this;
    }

    public Plano removeSeller(Seller seller) {
        this.sellers.remove(seller);
        seller.setPlano(null);
        return this;
    }

    public void setSellers(Set<Seller> sellers) {
        this.sellers = sellers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plano)) {
            return false;
        }
        return id != null && id.equals(((Plano) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plano{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
