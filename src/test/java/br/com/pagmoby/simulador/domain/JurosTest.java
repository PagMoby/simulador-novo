package br.com.pagmoby.simulador.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pagmoby.simulador.web.rest.TestUtil;

public class JurosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Juros.class);
        Juros juros1 = new Juros();
        juros1.setId(1L);
        Juros juros2 = new Juros();
        juros2.setId(juros1.getId());
        assertThat(juros1).isEqualTo(juros2);
        juros2.setId(2L);
        assertThat(juros1).isNotEqualTo(juros2);
        juros1.setId(null);
        assertThat(juros1).isNotEqualTo(juros2);
    }
}
