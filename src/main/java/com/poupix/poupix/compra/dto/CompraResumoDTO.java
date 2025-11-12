package com.poupix.poupix.compra.dto;

import com.poupix.poupix.compra.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraResumoDTO(
        Long id,
        String descricao,
        String nomeLoja,
        BigDecimal preco,
        LocalDate data,
        Pagamento formaDePagamento
) {
}
