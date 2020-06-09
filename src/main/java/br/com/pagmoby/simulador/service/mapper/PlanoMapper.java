package br.com.pagmoby.simulador.service.mapper;


import br.com.pagmoby.simulador.domain.*;
import br.com.pagmoby.simulador.service.dto.PlanoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plano} and its DTO {@link PlanoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanoMapper extends EntityMapper<PlanoDTO, Plano> {


    @Mapping(target = "juros", ignore = true)
    @Mapping(target = "removeJuros", ignore = true)
    @Mapping(target = "sellers", ignore = true)
    @Mapping(target = "removeSeller", ignore = true)
    Plano toEntity(PlanoDTO planoDTO);

    default Plano fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plano plano = new Plano();
        plano.setId(id);
        return plano;
    }
}
