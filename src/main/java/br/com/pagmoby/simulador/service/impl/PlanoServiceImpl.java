package br.com.pagmoby.simulador.service.impl;

import br.com.pagmoby.simulador.service.PlanoService;
import br.com.pagmoby.simulador.domain.Plano;
import br.com.pagmoby.simulador.repository.PlanoRepository;
import br.com.pagmoby.simulador.service.dto.PlanoDTO;
import br.com.pagmoby.simulador.service.mapper.PlanoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Plano}.
 */
@Service
@Transactional
public class PlanoServiceImpl implements PlanoService {

    private final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);

    private final PlanoRepository planoRepository;

    private final PlanoMapper planoMapper;

    public PlanoServiceImpl(PlanoRepository planoRepository, PlanoMapper planoMapper) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
    }

    /**
     * Save a plano.
     *
     * @param planoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlanoDTO save(PlanoDTO planoDTO) {
        log.debug("Request to save Plano : {}", planoDTO);
        Plano plano = planoMapper.toEntity(planoDTO);
        plano = planoRepository.save(plano);
        return planoMapper.toDto(plano);
    }

    /**
     * Get all the planos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Planos");
        return planoRepository.findAll(pageable)
            .map(planoMapper::toDto);
    }


    /**
     * Get one plano by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoDTO> findOne(Long id) {
        log.debug("Request to get Plano : {}", id);
        return planoRepository.findById(id)
            .map(planoMapper::toDto);
    }

    /**
     * Delete the plano by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plano : {}", id);
        planoRepository.deleteById(id);
    }
}
