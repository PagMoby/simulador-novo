package br.com.pagmoby.simulador.service.mapper;


import br.com.pagmoby.simulador.domain.*;
import br.com.pagmoby.simulador.service.dto.JurosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Juros} and its DTO {@link JurosDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class})
public interface JurosMapper extends EntityMapper<JurosDTO, Juros> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "plano.nome", target = "planoNome")
    JurosDTO toDto(Juros juros);

    @Mapping(source = "planoId", target = "plano")
    Juros toEntity(JurosDTO jurosDTO);

    default Juros fromId(Long id) {
        if (id == null) {
            return null;
        }
        Juros juros = new Juros();
        juros.setId(id);
        return juros;
    }
}
