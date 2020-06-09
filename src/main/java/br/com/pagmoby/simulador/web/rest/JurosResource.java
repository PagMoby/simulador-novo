package br.com.pagmoby.simulador.web.rest;

import br.com.pagmoby.simulador.security.SecurityUtils;
import br.com.pagmoby.simulador.service.JurosService;
import br.com.pagmoby.simulador.service.dto.TabelaDTO;
import br.com.pagmoby.simulador.web.rest.errors.BadRequestAlertException;
import br.com.pagmoby.simulador.service.dto.JurosDTO;

import br.com.pagmoby.simulador.web.rest.errors.NotFoundValueException;
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

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.pagmoby.simulador.domain.Juros}.
 */
@RestController
@RequestMapping("/api")
public class JurosResource {

    private final Logger log = LoggerFactory.getLogger(JurosResource.class);

    private static final String ENTITY_NAME = "juros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JurosService jurosService;

    public JurosResource(JurosService jurosService) {
        this.jurosService = jurosService;
    }

    /**
     * {@code POST  /juros} : Create a new juros.
     *
     * @param jurosDTO the jurosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jurosDTO, or with status {@code 400 (Bad Request)} if the juros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/juros")
    public ResponseEntity<JurosDTO> createJuros(@RequestBody JurosDTO jurosDTO) throws URISyntaxException {
        log.debug("REST request to save Juros : {}", jurosDTO);
        if (jurosDTO.getId() != null) {
            throw new BadRequestAlertException("A new juros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JurosDTO result = jurosService.save(jurosDTO);
        return ResponseEntity.created(new URI("/api/juros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /juros} : Updates an existing juros.
     *
     * @param jurosDTO the jurosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jurosDTO,
     * or with status {@code 400 (Bad Request)} if the jurosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jurosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/juros")
    public ResponseEntity<JurosDTO> updateJuros(@RequestBody JurosDTO jurosDTO) throws URISyntaxException {
        log.debug("REST request to update Juros : {}", jurosDTO);
        if (jurosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JurosDTO result = jurosService.save(jurosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jurosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /juros} : get all the juros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of juros in body.
     */
    @GetMapping("/juros")
    public ResponseEntity<List<JurosDTO>> getAllJuros(Pageable pageable) {
        log.debug("REST request to get a page of Juros");
        Page<JurosDTO> page = jurosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /juros/:id} : get the "id" juros.
     *
     * @param id the id of the jurosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jurosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/juros/{id}")
    public ResponseEntity<JurosDTO> getJuros(@PathVariable Long id) {
        log.debug("REST request to get Juros : {}", id);
        Optional<JurosDTO> jurosDTO = jurosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jurosDTO);
    }

    /**
     * {@code DELETE  /juros/:id} : delete the "id" juros.
     *
     * @param id the id of the jurosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/juros/{id}")
    public ResponseEntity<Void> deleteJuros(@PathVariable Long id) {
        log.debug("REST request to delete Juros : {}", id);
        jurosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code POST  /juros} : Create a new juros.
     *
     * @param valor the juros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new juros, or with status {@code 400 (Bad Request)} if the juros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/simular/{valor}/{semJuros}")
    public ResponseEntity<List<TabelaDTO>> createJuros(@PathVariable BigDecimal valor, @PathVariable Boolean semJuros)
        throws URISyntaxException {
        log.debug("Simular : {}", valor);
        if (valor == null) {
            throw new BadRequestAlertException("Valor obrigatório", ENTITY_NAME, "idexists");
        }
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(NotFoundValueException::new);

        Optional<List<TabelaDTO>> result = jurosService.simular(login, valor, semJuros);
        return ResponseEntity.ok().body(result.get());
    }
}
