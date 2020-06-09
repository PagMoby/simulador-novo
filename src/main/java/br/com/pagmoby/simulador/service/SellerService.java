package br.com.pagmoby.simulador.service;

import br.com.pagmoby.simulador.service.dto.SellerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.pagmoby.simulador.domain.Seller}.
 */
public interface SellerService {

    /**
     * Save a seller.
     *
     * @param sellerDTO the entity to save.
     * @return the persisted entity.
     */
    SellerDTO save(SellerDTO sellerDTO);

    /**
     * Get all the sellers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SellerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" seller.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SellerDTO> findOne(Long id);

    /**
     * Delete the "id" seller.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
