package br.com.pagmoby.simulador.service.impl;

import br.com.pagmoby.simulador.service.SellerService;
import br.com.pagmoby.simulador.domain.Seller;
import br.com.pagmoby.simulador.repository.SellerRepository;
import br.com.pagmoby.simulador.service.dto.SellerDTO;
import br.com.pagmoby.simulador.service.mapper.SellerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Seller}.
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    private final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
    }

    /**
     * Save a seller.
     *
     * @param sellerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SellerDTO save(SellerDTO sellerDTO) {
        log.debug("Request to save Seller : {}", sellerDTO);
        Seller seller = sellerMapper.toEntity(sellerDTO);
        seller = sellerRepository.save(seller);
        return sellerMapper.toDto(seller);
    }

    /**
     * Get all the sellers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SellerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sellers");
        return sellerRepository.findAll(pageable)
            .map(sellerMapper::toDto);
    }


    /**
     * Get one seller by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SellerDTO> findOne(Long id) {
        log.debug("Request to get Seller : {}", id);
        return sellerRepository.findById(id)
            .map(sellerMapper::toDto);
    }

    /**
     * Delete the seller by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seller : {}", id);
        sellerRepository.deleteById(id);
    }
}
