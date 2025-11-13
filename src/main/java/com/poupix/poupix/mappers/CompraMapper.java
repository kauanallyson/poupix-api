package com.poupix.poupix.mappers;

import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.dtos.compra.CompraCreateDTO;
import com.poupix.poupix.dtos.compra.CompraResumoDTO;
import com.poupix.poupix.dtos.compra.CompraUpdateDTO;
import com.poupix.poupix.exceptions.ResourceNotFoundException;
import com.poupix.poupix.entities.Loja;
import com.poupix.poupix.repositories.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompraMapper {
    private final LojaRepository lojaRepository;

    public void updateCompraFromDto(CompraUpdateDTO dto, Compra compra) {
        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + dto.lojaId() + " não encontrada"));

        compra.setDescricao(dto.descricao());
        compra.setLoja(loja);
        compra.setPreco(dto.preco());
        compra.setData(dto.data());
        compra.setFormaDePagamento(dto.formaDePagamento());
    }

    public CompraResumoDTO toResumoDTO(Compra compra) {
        return new CompraResumoDTO(
                compra.getId(),
                compra.getDescricao(),
                compra.getLoja().getNome(),
                compra.getPreco(),
                compra.getData(),
                compra.getFormaDePagamento());
    }

    public List<CompraResumoDTO> toResumoDTOList(List<Compra> compras) {
        return compras.stream().map(this::toResumoDTO).toList();
    }

    public Compra toEntity(CompraCreateDTO dto) {
        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + dto.lojaId() + " não encontrada"));

        return Compra.builder()
                .descricao(dto.descricao())
                .loja(loja)
                .preco(dto.preco())
                .data(dto.data())
                .formaDePagamento(dto.formaDePagamento())
                .build();
    }
}
