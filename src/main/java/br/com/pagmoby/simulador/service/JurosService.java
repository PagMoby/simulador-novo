package br.com.pagmoby.simulador.service;

import br.com.pagmoby.simulador.service.dto.JurosDTO;

import br.com.pagmoby.simulador.service.dto.TabelaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.pagmoby.simulador.domain.Juros}.
 */
public interface JurosService {

    /**
     * Save a juros.
     *
     * @param jurosDTO the entity to save.
     * @return the persisted entity.
     */
    JurosDTO save(JurosDTO jurosDTO);

    /**
     * Get all the juros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JurosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" juros.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JurosDTO> findOne(Long id);

    /**
     * Delete the "id" juros.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<List<TabelaDTO>> simular(String email, BigDecimal valor, Boolean semJuros);
}
