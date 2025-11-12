package com.poupix.poupix.loja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LojaCreateDTO(
        @NotBlank @Size(max = 100)
        String nome,

        @Size(max = 100)
        String categoria,

        Boolean favorito
) {
}
