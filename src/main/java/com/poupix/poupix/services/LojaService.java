package com.poupix.poupix.services;

import com.poupix.poupix.entities.Loja;
import com.poupix.poupix.exceptions.ResourceNotFoundException;
import com.poupix.poupix.dtos.loja.LojaCreateDTO;
import com.poupix.poupix.dtos.loja.LojaResponseDTO;
import com.poupix.poupix.dtos.loja.LojaUpdateDTO;
import com.poupix.poupix.mappers.LojaMapper;
import com.poupix.poupix.repositories.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LojaService {

    private final LojaRepository lojaRepository;
    private final LojaMapper mapper = new LojaMapper();

    public LojaResponseDTO criar(LojaCreateDTO dto) {
        var loja = mapper.toEntity(dto);
        return mapper.toResponseDTO(lojaRepository.save(loja));
    }

    public List<Loja> listar(Boolean favorito, String categoria) {
        return lojaRepository.findByFilters(favorito, categoria);
    }

    public LojaResponseDTO buscarPorId(Long id) {
        var loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + id + " não encontrada"));
        return mapper.toResponseDTO(loja);
    }

    public LojaResponseDTO atualizar(Long id, LojaUpdateDTO dto) {
        var loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + id + " não encontrada"));
        mapper.updateFromDto(dto, loja);
        return mapper.toResponseDTO(lojaRepository.save(loja));
    }

    public void favoritar(Long id){
        var loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + id + " não encontrada"));
        loja.setFavorito(!loja.getFavorito());
    }

    public void deletar(Long id) {
        if (!lojaRepository.existsById(id))
            throw new ResourceNotFoundException("Loja com id " + id + " não encontrada");
        lojaRepository.deleteById(id);
    }
}
