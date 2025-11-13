package com.poupix.poupix.dtos.compra;

import com.poupix.poupix.enums.Pagamento;

import java.math.BigDecimal;

public record ResumoPagamentoDTO(
        Pagamento formaDePagamento,
        BigDecimal totalGasto
) {
}
