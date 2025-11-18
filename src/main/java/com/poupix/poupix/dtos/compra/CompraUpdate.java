package com.poupix.poupix.dtos.compra;

import com.poupix.poupix.enums.Pagamento;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraUpdate(
        @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
        String descricao,

        Long lojaId,

        @Positive(message = "O preço da compra deve ser positivo")
        BigDecimal preco,

        @PastOrPresent(message = "Não é possível registrar uma compra no futuro")
        LocalDate data,

        Pagamento formaDePagamento
) {
}
