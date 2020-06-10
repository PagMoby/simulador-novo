package br.com.pagmoby.simulador.repository;

import br.com.pagmoby.simulador.domain.Juros;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Juros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JurosRepository extends JpaRepository<Juros, Long> {

    @Query("SELECT a FROM Juros a WHERE a.plano.id = ?1 ORDER BY a.id")
    List<Juros> findJurosByPlano(Long id);
}
