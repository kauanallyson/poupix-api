package com.poupix.poupix.compra.dto;

import com.poupix.poupix.compra.Pagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraCreateDTO(

        @NotNull
        @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
        String descricao,

        @NotNull(message = "É necessário informar o ID da loja onde foi feita a compra")
        Long lojaId,

        @NotNull
        @Positive(message = "O preço da compra deve ser positivo")
        BigDecimal preco,

        @NotNull
        @PastOrPresent(message = "Não é possível registrar uma compra no futuro")
        LocalDate data,

        @NotNull(message = "A forma de pagamento é obrigatória")
        Pagamento formaDePagamento
) {
}
