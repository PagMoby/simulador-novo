package br.com.pagmoby.simulador.repository;

import br.com.pagmoby.simulador.domain.Plano;
import br.com.pagmoby.simulador.domain.Seller;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Seller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT a FROM Seller a WHERE a.email = ?1")
    Optional<Seller> findSellerByEmail(String email);
}
