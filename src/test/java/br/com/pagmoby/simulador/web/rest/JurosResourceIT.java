package br.com.pagmoby.simulador.web.rest;

import br.com.pagmoby.simulador.SimuladorApp;
import br.com.pagmoby.simulador.domain.Juros;
import br.com.pagmoby.simulador.repository.JurosRepository;
import br.com.pagmoby.simulador.service.JurosService;
import br.com.pagmoby.simulador.service.dto.JurosDTO;
import br.com.pagmoby.simulador.service.mapper.JurosMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.pagmoby.simulador.domain.enumeration.Operacao;
/**
 * Integration tests for the {@link JurosResource} REST controller.
 */
@SpringBootTest(classes = SimuladorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JurosResourceIT {

    private static final BigDecimal DEFAULT_JUROS = new BigDecimal(1);
    private static final BigDecimal UPDATED_JUROS = new BigDecimal(2);

    private static final Operacao DEFAULT_OPERACAO = Operacao.DEBITO;
    private static final Operacao UPDATED_OPERACAO = Operacao.CREDITO1X;

    @Autowired
    private JurosRepository jurosRepository;

    @Autowired
    private JurosMapper jurosMapper;

    @Autowired
    private JurosService jurosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJurosMockMvc;

    private Juros juros;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Juros createEntity(EntityManager em) {
        Juros juros = new Juros()
            .juros(DEFAULT_JUROS)
            .operacao(DEFAULT_OPERACAO);
        return juros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Juros createUpdatedEntity(EntityManager em) {
        Juros juros = new Juros()
            .juros(UPDATED_JUROS)
            .operacao(UPDATED_OPERACAO);
        return juros;
    }

    @BeforeEach
    public void initTest() {
        juros = createEntity(em);
    }

    @Test
    @Transactional
    public void createJuros() throws Exception {
        int databaseSizeBeforeCreate = jurosRepository.findAll().size();
        // Create the Juros
        JurosDTO jurosDTO = jurosMapper.toDto(juros);
        restJurosMockMvc.perform(post("/api/juros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurosDTO)))
            .andExpect(status().isCreated());

        // Validate the Juros in the database
        List<Juros> jurosList = jurosRepository.findAll();
        assertThat(jurosList).hasSize(databaseSizeBeforeCreate + 1);
        Juros testJuros = jurosList.get(jurosList.size() - 1);
        assertThat(testJuros.getJuros()).isEqualTo(DEFAULT_JUROS);
        assertThat(testJuros.getOperacao()).isEqualTo(DEFAULT_OPERACAO);
    }

    @Test
    @Transactional
    public void createJurosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jurosRepository.findAll().size();

        // Create the Juros with an existing ID
        juros.setId(1L);
        JurosDTO jurosDTO = jurosMapper.toDto(juros);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJurosMockMvc.perform(post("/api/juros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Juros in the database
        List<Juros> jurosList = jurosRepository.findAll();
        assertThat(jurosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJuros() throws Exception {
        // Initialize the database
        jurosRepository.saveAndFlush(juros);

        // Get all the jurosList
        restJurosMockMvc.perform(get("/api/juros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(juros.getId().intValue())))
            .andExpect(jsonPath("$.[*].juros").value(hasItem(DEFAULT_JUROS.intValue())))
            .andExpect(jsonPath("$.[*].operacao").value(hasItem(DEFAULT_OPERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getJuros() throws Exception {
        // Initialize the database
        jurosRepository.saveAndFlush(juros);

        // Get the juros
        restJurosMockMvc.perform(get("/api/juros/{id}", juros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(juros.getId().intValue()))
            .andExpect(jsonPath("$.juros").value(DEFAULT_JUROS.intValue()))
            .andExpect(jsonPath("$.operacao").value(DEFAULT_OPERACAO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJuros() throws Exception {
        // Get the juros
        restJurosMockMvc.perform(get("/api/juros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJuros() throws Exception {
        // Initialize the database
        jurosRepository.saveAndFlush(juros);

        int databaseSizeBeforeUpdate = jurosRepository.findAll().size();

        // Update the juros
        Juros updatedJuros = jurosRepository.findById(juros.getId()).get();
        // Disconnect from session so that the updates on updatedJuros are not directly saved in db
        em.detach(updatedJuros);
        updatedJuros
            .juros(UPDATED_JUROS)
            .operacao(UPDATED_OPERACAO);
        JurosDTO jurosDTO = jurosMapper.toDto(updatedJuros);

        restJurosMockMvc.perform(put("/api/juros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurosDTO)))
            .andExpect(status().isOk());

        // Validate the Juros in the database
        List<Juros> jurosList = jurosRepository.findAll();
        assertThat(jurosList).hasSize(databaseSizeBeforeUpdate);
        Juros testJuros = jurosList.get(jurosList.size() - 1);
        assertThat(testJuros.getJuros()).isEqualTo(UPDATED_JUROS);
        assertThat(testJuros.getOperacao()).isEqualTo(UPDATED_OPERACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingJuros() throws Exception {
        int databaseSizeBeforeUpdate = jurosRepository.findAll().size();

        // Create the Juros
        JurosDTO jurosDTO = jurosMapper.toDto(juros);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJurosMockMvc.perform(put("/api/juros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Juros in the database
        List<Juros> jurosList = jurosRepository.findAll();
        assertThat(jurosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJuros() throws Exception {
        // Initialize the database
        jurosRepository.saveAndFlush(juros);

        int databaseSizeBeforeDelete = jurosRepository.findAll().size();

        // Delete the juros
        restJurosMockMvc.perform(delete("/api/juros/{id}", juros.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Juros> jurosList = jurosRepository.findAll();
        assertThat(jurosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
