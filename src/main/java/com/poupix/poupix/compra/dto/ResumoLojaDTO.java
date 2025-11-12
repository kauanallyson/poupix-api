package com.poupix.poupix.compra.dto;

import java.math.BigDecimal;

public record ResumoLojaDTO(
        String nomeLoja,
        BigDecimal totalGasto
) {
}
