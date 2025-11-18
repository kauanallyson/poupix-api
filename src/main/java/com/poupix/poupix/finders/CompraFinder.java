package com.poupix.poupix.finders;

import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.exceptions.ResourceNotFoundException;
import com.poupix.poupix.repositories.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompraFinder {
    private final CompraRepository compraRepository;

    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra com id " + id + " não encontrada"));
    }
}
