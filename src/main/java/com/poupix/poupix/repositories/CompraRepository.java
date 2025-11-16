package com.poupix.poupix.repositories;

import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.enums.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Query("""
            SELECT c FROM Compra c
            JOIN FETCH c.loja
            WHERE (:ano is NULL OR YEAR(c.data) = :ano)
            AND (:mes is NULL OR MONTH(c.data) = :mes)
            AND (:pagamento IS NULL OR c.formaDePagamento = :pagamento)
            ORDER BY c.data
            """)
    List<Compra> findByFilters(@Param("ano") int ano, @Param("mes") int mes, @Param("pagamento") Pagamento pagamento);
}
