package com.poupix.poupix.repositories;

import com.poupix.poupix.entities.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
    @Query("""
            SELECT l FROM Loja l
            WHERE (:favorito IS NULL OR l.favorito = :favorito)
            AND (:categoria IS NULL OR LOWER(l.categoria) = LOWER(:categoria))
            ORDER BY l.favorito
            """)
    List<Loja> findByFilters(@Param("favorito") Boolean favorito, @Param("categoria") String categoria);
}
