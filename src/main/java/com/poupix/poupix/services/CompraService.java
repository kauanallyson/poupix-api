package com.poupix.poupix.services;

import com.poupix.poupix.dtos.compra.*;
import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.entities.Loja;
import com.poupix.poupix.enums.Pagamento;
import com.poupix.poupix.exceptions.ResourceNotFoundException;
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
    private final CompraMapper mapper;

    @Transactional
    public Compra criar(CompraCreateDTO dto) {
        var compra = mapper.toEntity(dto);
        return compraRepository.save(compra);
    }

    @Transactional(readOnly = true)
    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));
    }

    @Transactional
    public Compra atualizar(Long id, CompraUpdateDTO dto) {
        var compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));

        mapper.updateCompraFromDto(dto, compra);
        return compraRepository.save(compra);
    }

    @Transactional
    public void deletar(Long id) {
        var compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));
        compraRepository.delete(compra);
    }

    @Transactional(readOnly = true)
    public RelatorioMensalDTO relatorioMensal(int ano, int mes, Pagamento pagamento) {
        validarAnoMes(ano, mes);

        var compras = buscarComprasDoMes(ano, mes, pagamento);

        if (compras.isEmpty()) {
            return criarRelatorioVazio(ano, mes);
        }

        var totalGasto = calcularTotalGasto(compras);

        var gastoPorLoja = calcularGastoPorLoja(compras);

        var gastoPorPagamento = calcularGastoPorPagamento(compras);

        var totalPorLoja = gastoPorLoja.entrySet().stream()
                .map(entry -> new ResumoLojaDTO(entry.getKey(), entry.getValue()))
                .toList();

        var totalPorPagamento = gastoPorPagamento.entrySet().stream()
                .map(entry -> new ResumoPagamentoDTO(entry.getKey(), entry.getValue()))
                .toList();

        var comprasResumo = mapper.toResumoDTOList(compras);

        return new RelatorioMensalDTO(ano, mes, totalGasto, totalPorLoja, totalPorPagamento, comprasResumo);
    }

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
                List.of()
        );
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
                        Collectors.reducing(BigDecimal.ZERO, Compra::getPreco, BigDecimal::add)
                ));
    }

    private Map<Pagamento, BigDecimal> calcularGastoPorPagamento(List<Compra> compras) {
        return compras.stream()
                .collect(Collectors.groupingBy(
                        Compra::getFormaDePagamento,
                        Collectors.reducing(BigDecimal.ZERO, Compra::getPreco, BigDecimal::add)
                ));
    }
}