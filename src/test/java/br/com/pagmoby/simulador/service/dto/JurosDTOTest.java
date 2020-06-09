package br.com.pagmoby.simulador.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pagmoby.simulador.web.rest.TestUtil;

public class JurosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JurosDTO.class);
        JurosDTO jurosDTO1 = new JurosDTO();
        jurosDTO1.setId(1L);
        JurosDTO jurosDTO2 = new JurosDTO();
        assertThat(jurosDTO1).isNotEqualTo(jurosDTO2);
        jurosDTO2.setId(jurosDTO1.getId());
        assertThat(jurosDTO1).isEqualTo(jurosDTO2);
        jurosDTO2.setId(2L);
        assertThat(jurosDTO1).isNotEqualTo(jurosDTO2);
        jurosDTO1.setId(null);
        assertThat(jurosDTO1).isNotEqualTo(jurosDTO2);
    }
}
