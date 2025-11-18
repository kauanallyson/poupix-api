package com.poupix.poupix.dtos.loja;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LojaCreate(
        @NotBlank @Size(max = 100)
        String nome,

        @Size(max = 100)
        String categoria,

        Boolean favorito
) {
    public LojaCreate {
        favorito = favorito != null ? favorito : false;
    }
}
