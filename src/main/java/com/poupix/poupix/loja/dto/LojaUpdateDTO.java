package com.poupix.poupix.loja.dto;

import jakarta.validation.constraints.Size;

public record LojaUpdateDTO(
        @Size(max = 100)
        String nome,

        @Size(max = 100)
        String categoria,

        Boolean favorito
) {
}
