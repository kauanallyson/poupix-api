package com.poupix.poupix.services;

import com.poupix.poupix.dtos.compra.*;
import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.enums.Pagamento;
import com.poupix.poupix.finders.CompraFinder;
import com.poupix.poupix.finders.LojaFinder;
import com.poupix.poupix.mappers.CompraMapper;
import com.poupix.poupix.repositories.CompraRepository;
import com.poupix.poupix.repositories.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final LojaRepository lojaRepository;
    private final CompraFinder compraFinder;
    private final LojaFinder lojaFinder;
    private final CompraMapper compraMapper;

    @Transactional
    public void criar(CompraCreateDTO dto) {
        var loja = lojaFinder.buscarPorId(dto.lojaId());
        var compra = compraMapper.toEntity(dto, loja);
        compraRepository.save(compra);
    }


    @Transactional(readOnly = true)
    public List<CompraResponseDTO> listarComFiltros(int ano, int mes, Pagamento pagamento) {
        var compras = compraRepository.findByFilters(ano,mes,pagamento);
        return compraMapper.toResumoDTOList(compras);
    }

    @Transactional(readOnly = true)
    public CompraResponseDTO buscarPorId(Long id) {
        var compra = compraFinder.buscarPorId(id);
        return compraMapper.toResponseDTO(compra);
    }

    @Transactional
    public CompraResponseDTO atualizar(Long id, CompraUpdateDTO dto) {
        var loja = lojaFinder.buscarPorId(dto.lojaId());
        var compra = compraFinder.buscarPorId(id);

        compraMapper.updateCompraFromDto(dto, compra, loja);
        compraRepository.save(compra);
        return compraMapper.toResponseDTO(compra);
    }

    @Transactional
    public void deletar(Long id) {
        var compra = compraFinder.buscarPorId(id);
        compraRepository.delete(compra);
    }

    @Transactional(readOnly = true)
    public RelatorioMensalDTO relatorioMensal(int ano, int mes, Pagamento pagamento) {
        validarAnoMes(ano, mes);

        var compras = buscarComprasDoMes(ano, mes, pagamento);

        if (compras.isEmpty()) {
            return criarRelatorioVazio(ano, mes);
        }

        BigDecimal totalGasto = calcularTotalGasto(compras);

        Map<String, BigDecimal> gastoPorLoja = calcularGastoPorLoja(compras);

        Map<Pagamento, BigDecimal> gastoPorPagamento = calcularGastoPorPagamento(compras);

        List<ResumoLojaDTO> totalPorLoja = gastoPorLoja.entrySet().stream()
                .map(entry -> new ResumoLojaDTO(entry.getKey(), entry.getValue()))
                .toList();

        List<ResumoPagamentoDTO> totalPorPagamento = gastoPorPagamento.entrySet().stream()
                .map(entry -> new ResumoPagamentoDTO(entry.getKey(), entry.getValue()))
                .toList();

        List<CompraResponseDTO> compraResponse = compraMapper.toResumoDTOList(compras);

        return new RelatorioMensalDTO(ano, mes, totalGasto, totalPorLoja, totalPorPagamento, compraResponse);
    }

    /*
     * MÉTODOS PRIVADOS
     * */

    private void validarAnoMes(int ano, int mes) {
        if (ano < 2000 || ano > 2100) {
            throw new IllegalArgumentException("Ano deve estar entre 2000 e 2100");
        }

        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês deve estar entre 1 e 12");
        }
    }

    private RelatorioMensalDTO criarRelatorioVazio(int ano, int mes) {
        return new RelatorioMensalDTO(
                ano,
                mes,
                BigDecimal.ZERO,
                List.of(),
                List.of(),
                List.of());
    }

    private List<Compra> buscarComprasDoMes(int ano, int mes, Pagamento pagamento) {
        return compraRepository.findByFilters(ano, mes, pagamento);
    }

    private BigDecimal calcularTotalGasto(List<Compra> compras) {
        return compras.stream()
                .map(Compra::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, BigDecimal> calcularGastoPorLoja(List<Compra> compras) {
        return compras.stream()
                .collect(Collectors.groupingBy(
                        compra -> compra.getLoja().getNome(),
                        Collectors.reducing(BigDecimal.ZERO, Compra::getPreco, BigDecimal::add)));
    }

    private Map<Pagamento, BigDecimal> calcularGastoPorPagamento(List<Compra> compras) {
        return compras.stream()
                .collect(Collectors.groupingBy(
                        Compra::getFormaDePagamento,
                        Collectors.reducing(BigDecimal.ZERO, Compra::getPreco, BigDecimal::add)));
    }
}