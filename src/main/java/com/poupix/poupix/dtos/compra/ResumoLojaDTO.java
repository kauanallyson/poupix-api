package com.poupix.poupix.dtos.compra;

import java.math.BigDecimal;

public record ResumoLojaDTO(
        String nomeLoja,
        BigDecimal totalGasto
) {
}
