package com.poupix.poupix.dtos.compra;

import java.math.BigDecimal;

public record ResumoLoja(
        String nomeLoja,
        BigDecimal totalGasto
) {
}
