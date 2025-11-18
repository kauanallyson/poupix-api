package com.poupix.poupix.mappers;

import com.poupix.poupix.dtos.loja.LojaCreate;
import com.poupix.poupix.dtos.loja.LojaResponse;
import com.poupix.poupix.dtos.loja.LojaUpdate;
import com.poupix.poupix.entities.Loja;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LojaMapper {

    public Loja toEntity(LojaCreate dto) {
        return Loja.builder()
                .nome(dto.nome())
                .categoria(dto.categoria())
                .favorito(dto.favorito() != null ? dto.favorito() : false)
                .build();
    }

    public LojaResponse toResponseDTO(Loja loja) {
        return new LojaResponse(
                loja.getId(),
                loja.getNome(),
                loja.getCategoria(),
                loja.getFavorito()
        );
    }

    public List<LojaResponse> toResponseDTOList(List<Loja> lojas) {
        return lojas.stream().map(this::toResponseDTO).toList();
    }

    public void updateFromDto(LojaUpdate dto, Loja loja) {
        if (dto.nome() != null) loja.setNome(dto.nome());
        if (dto.categoria() != null) loja.setCategoria(dto.categoria());
        if (dto.favorito() != null) loja.setFavorito(dto.favorito());
    }
}
