package br.com.pagmoby.simulador.web.rest;

import br.com.pagmoby.simulador.SimuladorApp;
import br.com.pagmoby.simulador.domain.Seller;
import br.com.pagmoby.simulador.repository.SellerRepository;
import br.com.pagmoby.simulador.service.SellerService;
import br.com.pagmoby.simulador.service.dto.SellerDTO;
import br.com.pagmoby.simulador.service.mapper.SellerMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SellerResource} REST controller.
 */
@SpringBootTest(classes = SimuladorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SellerResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSellerMockMvc;

    private Seller seller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seller createEntity(EntityManager em) {
        Seller seller = new Seller()
            .email(DEFAULT_EMAIL);
        return seller;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seller createUpdatedEntity(EntityManager em) {
        Seller seller = new Seller()
            .email(UPDATED_EMAIL);
        return seller;
    }

    @BeforeEach
    public void initTest() {
        seller = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeller() throws Exception {
        int databaseSizeBeforeCreate = sellerRepository.findAll().size();
        // Create the Seller
        SellerDTO sellerDTO = sellerMapper.toDto(seller);
        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isCreated());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeCreate + 1);
        Seller testSeller = sellerList.get(sellerList.size() - 1);
        assertThat(testSeller.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createSellerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sellerRepository.findAll().size();

        // Create the Seller with an existing ID
        seller.setId(1L);
        SellerDTO sellerDTO = sellerMapper.toDto(seller);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSellers() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);

        // Get all the sellerList
        restSellerMockMvc.perform(get("/api/sellers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seller.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getSeller() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);

        // Get the seller
        restSellerMockMvc.perform(get("/api/sellers/{id}", seller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seller.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingSeller() throws Exception {
        // Get the seller
        restSellerMockMvc.perform(get("/api/sellers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeller() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);

        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();

        // Update the seller
        Seller updatedSeller = sellerRepository.findById(seller.getId()).get();
        // Disconnect from session so that the updates on updatedSeller are not directly saved in db
        em.detach(updatedSeller);
        updatedSeller
            .email(UPDATED_EMAIL);
        SellerDTO sellerDTO = sellerMapper.toDto(updatedSeller);

        restSellerMockMvc.perform(put("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isOk());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
        Seller testSeller = sellerList.get(sellerList.size() - 1);
        assertThat(testSeller.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingSeller() throws Exception {
        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();

        // Create the Seller
        SellerDTO sellerDTO = sellerMapper.toDto(seller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellerMockMvc.perform(put("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeller() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);

        int databaseSizeBeforeDelete = sellerRepository.findAll().size();

        // Delete the seller
        restSellerMockMvc.perform(delete("/api/sellers/{id}", seller.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
