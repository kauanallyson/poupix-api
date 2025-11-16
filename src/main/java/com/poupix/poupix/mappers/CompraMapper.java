package com.poupix.poupix.mappers;

import com.poupix.poupix.dtos.compra.CompraCreateDTO;
import com.poupix.poupix.dtos.compra.CompraResponseDTO;
import com.poupix.poupix.dtos.compra.CompraUpdateDTO;
import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.entities.Loja;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompraMapper {

    public void updateCompraFromDto(CompraUpdateDTO dto, Compra compra, Loja loja) {
        compra.setDescricao(dto.descricao());
        compra.setLoja(loja);
        compra.setPreco(dto.preco());
        compra.setData(dto.data());
        compra.setFormaDePagamento(dto.formaDePagamento());
    }

    public CompraResponseDTO toResponseDTO(Compra compra) {
        return new CompraResponseDTO(
                compra.getId(),
                compra.getDescricao(),
                compra.getLoja().getNome(),
                compra.getPreco(),
                compra.getData(),
                compra.getFormaDePagamento());
    }

    public List<CompraResponseDTO> toResumoDTOList(List<Compra> compras) {
        return compras.stream().map(this::toResponseDTO).toList();
    }

    public Compra toEntity(CompraCreateDTO dto, Loja loja) {
        return Compra.builder()
                .descricao(dto.descricao())
                .loja(loja)
                .preco(dto.preco())
                .data(dto.data())
                .formaDePagamento(dto.formaDePagamento())
                .build();
    }
}
