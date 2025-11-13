package com.poupix.poupix.dtos.compra;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioMensalDTO(
        int ano,
        int mes,
        BigDecimal totalGasto,
        List<ResumoLojaDTO> totalPorLoja,
        List<ResumoPagamentoDTO> totalPorPagamento,
        List<CompraResumoDTO> compras
) {
}