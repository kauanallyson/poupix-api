package com.poupix.poupix.finders;

import com.poupix.poupix.entities.Loja;
import com.poupix.poupix.exceptions.ResourceNotFoundException;
import com.poupix.poupix.repositories.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LojaFinder {
    private final LojaRepository lojaRepository;

    public Loja buscarPorId(Long id) {
        return lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + id + " não encontrada"));
    }
}
