package br.com.pagmoby.simulador.web.rest;

import br.com.pagmoby.simulador.service.SellerService;
import br.com.pagmoby.simulador.web.rest.errors.BadRequestAlertException;
import br.com.pagmoby.simulador.service.dto.SellerDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.pagmoby.simulador.domain.Seller}.
 */
@RestController
@RequestMapping("/api")
public class SellerResource {

    private final Logger log = LoggerFactory.getLogger(SellerResource.class);

    private static final String ENTITY_NAME = "seller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SellerService sellerService;

    public SellerResource(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    /**
     * {@code POST  /sellers} : Create a new seller.
     *
     * @param sellerDTO the sellerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sellerDTO, or with status {@code 400 (Bad Request)} if the seller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sellers")
    public ResponseEntity<SellerDTO> createSeller(@RequestBody SellerDTO sellerDTO) throws URISyntaxException {
        log.debug("REST request to save Seller : {}", sellerDTO);
        if (sellerDTO.getId() != null) {
            throw new BadRequestAlertException("A new seller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellerDTO result = sellerService.save(sellerDTO);
        return ResponseEntity.created(new URI("/api/sellers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sellers} : Updates an existing seller.
     *
     * @param sellerDTO the sellerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellerDTO,
     * or with status {@code 400 (Bad Request)} if the sellerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sellerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sellers")
    public ResponseEntity<SellerDTO> updateSeller(@RequestBody SellerDTO sellerDTO) throws URISyntaxException {
        log.debug("REST request to update Seller : {}", sellerDTO);
        if (sellerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SellerDTO result = sellerService.save(sellerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sellerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sellers} : get all the sellers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sellers in body.
     */
    @GetMapping("/sellers")
    public ResponseEntity<List<SellerDTO>> getAllSellers(Pageable pageable) {
        log.debug("REST request to get a page of Sellers");
        Page<SellerDTO> page = sellerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sellers/:id} : get the "id" seller.
     *
     * @param id the id of the sellerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sellerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sellers/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable Long id) {
        log.debug("REST request to get Seller : {}", id);
        Optional<SellerDTO> sellerDTO = sellerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sellerDTO);
    }

    /**
     * {@code DELETE  /sellers/:id} : delete the "id" seller.
     *
     * @param id the id of the sellerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sellers/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        log.debug("REST request to delete Seller : {}", id);
        sellerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
