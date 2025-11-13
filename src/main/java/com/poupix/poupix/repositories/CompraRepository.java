package com.poupix.poupix.repositories;

import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.enums.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("""
                SELECT c FROM Compra c
                WHERE EXTRACT(YEAR FROM c.data) = :ano
                AND EXTRACT(MONTH FROM c.data) = :mes
                ORDER BY c.data
            """)
    List<Compra> findByAnoAndMes(@Param("ano") int ano, @Param("mes") int mes);

    @Query("""
                SELECT c FROM Compra c
                WHERE EXTRACT(YEAR FROM c.data) = :ano
                AND EXTRACT(MONTH FROM c.data) = :mes
                AND c.formaDePagamento = :pagamento
                ORDER BY c.data
            """)
    List<Compra> findByAnoAndMesAndPagamento(@Param("ano") int ano, @Param("mes") int mes, @Param("pagamento") Pagamento pagamento);
}
