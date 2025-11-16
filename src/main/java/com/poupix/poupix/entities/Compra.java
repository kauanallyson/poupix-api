package com.poupix.poupix.entities;

import com.poupix.poupix.enums.Pagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @Positive
    @Column(nullable = false)
    private BigDecimal preco;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Pagamento formaDePagamento;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Compra compra)) return false;
        return Objects.equals(id, compra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
