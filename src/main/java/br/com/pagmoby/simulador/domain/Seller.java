package br.com.pagmoby.simulador.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Seller.
 */
@Entity
@Table(name = "seller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JsonIgnoreProperties(value = "sellers", allowSetters = true)
    private Plano plano;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Seller() {
    }

    public Seller(String email) {
        this.email = email;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Seller email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Plano getPlano() {
        return plano;
    }

    public Seller plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seller)) {
            return false;
        }
        return id != null && id.equals(((Seller) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seller{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
