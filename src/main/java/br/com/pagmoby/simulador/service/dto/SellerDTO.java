package br.com.pagmoby.simulador.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.pagmoby.simulador.domain.Seller} entity.
 */
public class SellerDTO implements Serializable {

    private Long id;

    private String email;


    private Long planoId;

    private String planoNome;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellerDTO)) {
            return false;
        }

        return id != null && id.equals(((SellerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellerDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", planoId=" + getPlanoId() +
            ", planoNome='" + getPlanoNome() + "'" +
            "}";
    }
}
