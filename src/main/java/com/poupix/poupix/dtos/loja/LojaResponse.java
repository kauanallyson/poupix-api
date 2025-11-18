package com.poupix.poupix.dtos.loja;

public record LojaResponse(
        Long id,
        String nome,
        String categoria,
        Boolean favorito
) {
}
