package br.com.pagmoby.simulador.service.impl;

import br.com.pagmoby.simulador.domain.Plano;
import br.com.pagmoby.simulador.domain.Seller;
import br.com.pagmoby.simulador.repository.PlanoRepository;
import br.com.pagmoby.simulador.repository.SellerRepository;
import br.com.pagmoby.simulador.service.JurosService;
import br.com.pagmoby.simulador.domain.Juros;
import br.com.pagmoby.simulador.repository.JurosRepository;
import br.com.pagmoby.simulador.service.dto.JurosDTO;
import br.com.pagmoby.simulador.service.dto.TabelaDTO;
import br.com.pagmoby.simulador.service.mapper.JurosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Juros}.
 */
@Service
@Transactional
public class JurosServiceImpl implements JurosService {

    private final Logger log = LoggerFactory.getLogger(JurosServiceImpl.class);

    private final JurosRepository jurosRepository;

    private final JurosMapper jurosMapper;

    private final SellerRepository sellerRepository;

    private final PlanoRepository planoRepository;

    public JurosServiceImpl(JurosRepository jurosRepository, JurosMapper jurosMapper, SellerRepository sellerRepository, PlanoRepository planoRepository) {
        this.jurosRepository = jurosRepository;
        this.jurosMapper = jurosMapper;
        this.sellerRepository = sellerRepository;
        this.planoRepository = planoRepository;
    }

    /**
     * Save a juros.
     *
     * @param jurosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JurosDTO save(JurosDTO jurosDTO) {
        log.debug("Request to save Juros : {}", jurosDTO);
        Juros juros = jurosMapper.toEntity(jurosDTO);
        juros = jurosRepository.save(juros);
        return jurosMapper.toDto(juros);
    }

    /**
     * Get all the juros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JurosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Juros");
        return jurosRepository.findAll(pageable)
            .map(jurosMapper::toDto);
    }


    /**
     * Get one juros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JurosDTO> findOne(Long id) {
        log.debug("Request to get Juros : {}", id);
        return jurosRepository.findById(id)
            .map(jurosMapper::toDto);
    }

    /**
     * Delete the juros by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Juros : {}", id);
        jurosRepository.deleteById(id);
    }

    @Override
    public Optional<List<TabelaDTO>> simular(String email, BigDecimal valor, Boolean semJuros) {
        Optional<Seller> seller = sellerRepository.findSellerByEmail(email);
        List<TabelaDTO> tabela = new ArrayList<>();
        if (seller.isPresent()) {
            if (seller.get().getPlano() != null) {
                List<Juros> juros = jurosRepository.findJurosByPlano(seller.get().getPlano().getId());
                for (Juros juro : juros) {
                    TabelaDTO tabelaDTO = new TabelaDTO();
                    if (semJuros) {
                        tabelaDTO.setOperacao(juro.getOperacao());
                        tabelaDTO.setValorDaVenda(formatarValor(valor));
                        tabelaDTO.setValorReceber(semJuros(juro.getJuros(), valor));
                    } else {
                        tabelaDTO.setOperacao(juro.getOperacao());
                        tabelaDTO.setValorDaVenda(comJuros(juro.getJuros(), valor));
                        tabelaDTO.setValorReceber(formatarValor(valor));
                    }
                    tabela.add(tabelaDTO);
                }
            }
        }
        return Optional.of(tabela);
    }

    private String semJuros(BigDecimal juros, BigDecimal valor) {
        BigDecimal resposta = valor.subtract(valor.multiply(juros.divide(new BigDecimal("100.00"), RoundingMode.CEILING)));

        return formatarValor(resposta.setScale(2, RoundingMode.HALF_EVEN));
    }

    private String comJuros(BigDecimal juros, BigDecimal valor) {
        float valorFloat = valor.floatValue();
        float jurosFloat = juros.floatValue();

        double resultado = valorFloat / ( 1.00 - (jurosFloat / 100));
        BigDecimal b = new BigDecimal(resultado);

        return formatarValor(b.setScale(2, RoundingMode.CEILING));

    }

    private String formatarValor(BigDecimal valor){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format (valor);
    }

}
