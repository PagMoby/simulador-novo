package br.com.pagmoby.simulador.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JurosMapperTest {

    private JurosMapper jurosMapper;

    @BeforeEach
    public void setUp() {
        jurosMapper = new JurosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jurosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jurosMapper.fromId(null)).isNull();
    }
}
