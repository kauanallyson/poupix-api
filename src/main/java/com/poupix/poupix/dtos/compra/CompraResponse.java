package com.poupix.poupix.dtos.compra;

import com.poupix.poupix.enums.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraResponse(
        Long id,
        String descricao,
        String nomeLoja,
        BigDecimal preco,
        LocalDate data,
        Pagamento formaDePagamento
) {
}
