package com.poupix.poupix.compra;

import com.poupix.poupix.compra.dto.*;
import com.poupix.poupix.compra.mapper.CompraMapper;
import com.poupix.poupix.exceptions.ResourceNotFoundException;
import com.poupix.poupix.loja.Loja;
import com.poupix.poupix.loja.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {
    private final CompraRepository compraRepository;
    private final LojaRepository lojaRepository;
    private final CompraMapper compraMapper;

    public RelatorioMensalDTO relatorioMensal(int ano, int mes, Pagamento pagamento) {
        var compras = buscarComprasDoMes(ano, mes, pagamento);

        var totalGasto = calcularTotalGasto(compras);

        var gastoPorLoja = calcularGastoPorLoja(compras);

        var gastoPorPagamento = calcularGastoPorPagamento(compras);

        List<ResumoLojaDTO> totalPorLoja = gastoPorLoja.entrySet().stream()
                .map(entry -> new ResumoLojaDTO(entry.getKey(), entry.getValue()))
                .toList();

        List<ResumoPagamentoDTO> totalPorPagamento = gastoPorPagamento.entrySet().stream()
                .map(entry -> new ResumoPagamentoDTO(entry.getKey(), entry.getValue()))
                .toList();

        List<CompraResumoDTO> comprasResumo = compraMapper.toResumoDTOList(compras);

        return new RelatorioMensalDTO(ano, mes, totalGasto, totalPorLoja, totalPorPagamento, comprasResumo);
    }

    public Compra criar(CompraCreateDTO dto) {
        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + dto.lojaId() + " não encontrada"));

        Compra compra = compraMapper.toEntity(dto);
        compra.setLoja(loja);

        return compraRepository.save(compra);
    }


    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));
    }

    public Compra atualizar(Long id, CompraUpdateDTO dto) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));

        compraMapper.updateCompraFromDto(dto, compra);

        if (dto.lojaId() != null) {
            Loja loja = lojaRepository.findById(dto.lojaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + dto.lojaId() + " não encontrada"));
            compra.setLoja(loja);
        }

        return compraRepository.save(compra);
    }

    public void deletar(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));
        compraRepository.delete(compra);
    }

    private List<Compra> buscarComprasDoMes(int ano, int mes, Pagamento pagamento) {
        return (pagamento == null)
                ? compraRepository.findByAnoAndMes(ano, mes)
                : compraRepository.findByAnoAndMesAndPagamento(ano, mes, pagamento);
    }

    private BigDecimal calcularTotalGasto(List<Compra> compras) {
        return compras.stream()
                .map(Compra::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, BigDecimal> calcularGastoPorLoja(List<Compra> compras) {
        return compras.stream()
                .collect(Collectors.groupingBy(compra -> compra.getLoja().getNome(),
                        Collectors.reducing(BigDecimal.ZERO, Compra::getPreco, BigDecimal::add)
                ));
    }

    private Map<Pagamento, BigDecimal> calcularGastoPorPagamento(List<Compra> compras) {
        return compras.stream()
                .collect(Collectors.groupingBy(Compra::getFormaDePagamento,
                        Collectors.reducing(BigDecimal.ZERO, Compra::getPreco, BigDecimal::add)
                ));
    }
}
