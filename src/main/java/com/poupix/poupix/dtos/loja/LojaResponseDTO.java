package com.poupix.poupix.dtos.loja;

public record LojaResponseDTO(
        Long id,
        String nome,
        String categoria,
        Boolean favorito
) {
}
