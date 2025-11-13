package com.poupix.poupix.dtos.compra;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioMensalDTO(
        @Min(2000) @Max(2100)
        int ano,
        @Min(1) @Max(12)
        int mes,
        BigDecimal totalGasto,
        List<ResumoLojaDTO> totalPorLoja,
        List<ResumoPagamentoDTO> totalPorPagamento,
        List<CompraResumoDTO> compras
) {
}