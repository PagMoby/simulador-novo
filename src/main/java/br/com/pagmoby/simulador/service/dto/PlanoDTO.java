package br.com.pagmoby.simulador.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.pagmoby.simulador.domain.Plano} entity.
 */
public class PlanoDTO implements Serializable {
    
    private Long id;

    private String nome;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
