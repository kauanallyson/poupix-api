package com.poupix.poupix.repositories;

import com.poupix.poupix.entities.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {

    List<Loja> findByFavoritoTrue();

    List<Loja> findByCategoriaIgnoreCase(String categoria);

    List<Loja> findByFavoritoTrueAndCategoriaIgnoreCase(String categoria);
}
