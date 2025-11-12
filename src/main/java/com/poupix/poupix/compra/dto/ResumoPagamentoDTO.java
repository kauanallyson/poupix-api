package com.poupix.poupix.compra.dto;

import com.poupix.poupix.compra.Pagamento;

import java.math.BigDecimal;

public record ResumoPagamentoDTO(
        Pagamento formaDePagamento,
        BigDecimal totalGasto
) {
}
