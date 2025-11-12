package com.poupix.poupix.loja.mapper;

import com.poupix.poupix.loja.Loja;
import com.poupix.poupix.loja.dto.LojaCreateDTO;
import com.poupix.poupix.loja.dto.LojaResponseDTO;
import com.poupix.poupix.loja.dto.LojaUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LojaMapper {

    public Loja toEntity(LojaCreateDTO dto) {
        return Loja.builder()
                .nome(dto.nome())
                .categoria(dto.categoria())
                .favorito(dto.favorito() != null ? dto.favorito() : false)
                .build();
    }

    public LojaResponseDTO toResponseDTO(Loja loja) {
        return new LojaResponseDTO(
                loja.getId(),
                loja.getNome(),
                loja.getCategoria(),
                loja.getFavorito()
        );
    }

    public List<LojaResponseDTO> toResponseDTOList(List<Loja> lojas) {
        return lojas.stream().map(this::toResponseDTO).toList();
    }

    public void updateFromDto(LojaUpdateDTO dto, Loja loja) {
        if (dto.nome() != null) loja.setNome(dto.nome());
        if (dto.categoria() != null) loja.setCategoria(dto.categoria());
        if (dto.favorito() != null) loja.setFavorito(dto.favorito());
    }
}
