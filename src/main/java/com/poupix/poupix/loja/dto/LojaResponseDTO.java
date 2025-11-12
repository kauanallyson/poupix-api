package com.poupix.poupix.loja.dto;

public record LojaResponseDTO(
        Long id,
        String nome,
        String categoria,
        Boolean favorito
) {
}
