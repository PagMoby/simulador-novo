package br.com.pagmoby.simulador.service.mapper;


import br.com.pagmoby.simulador.domain.*;
import br.com.pagmoby.simulador.service.dto.SellerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seller} and its DTO {@link SellerDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class})
public interface SellerMapper extends EntityMapper<SellerDTO, Seller> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "plano.nome", target = "planoNome")
    SellerDTO toDto(Seller seller);

    @Mapping(source = "planoId", target = "plano")
    Seller toEntity(SellerDTO sellerDTO);

    default Seller fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }
}
