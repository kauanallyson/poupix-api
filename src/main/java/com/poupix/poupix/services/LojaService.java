package com.poupix.poupix.services;

import com.poupix.poupix.dtos.loja.LojaCreate;
import com.poupix.poupix.dtos.loja.LojaResponse;
import com.poupix.poupix.dtos.loja.LojaUpdate;
import com.poupix.poupix.finders.LojaFinder;
import com.poupix.poupix.mappers.LojaMapper;
import com.poupix.poupix.repositories.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final LojaFinder lojaFinder;
    private final LojaMapper lojaMapper;

    @Transactional
    public void criar(LojaCreate dto) {
        var loja = lojaMapper.toEntity(dto);
        lojaRepository.save(loja);
    }

    @Transactional(readOnly = true)
    public List<LojaResponse> listarComFiltros(Boolean favorito, String categoria) {
        var lojas = lojaRepository.findByFilters(favorito, categoria);
        return lojaMapper.toResponseDTOList(lojas);
    }

    @Transactional(readOnly = true)
    public LojaResponse buscarPorId(Long id) {
        var loja = lojaFinder.buscarPorId(id);
        return lojaMapper.toResponseDTO(loja);
    }

    @Transactional
    public LojaResponse atualizar(Long id, LojaUpdate dto) {
        var loja = lojaFinder.buscarPorId(id);
        lojaMapper.updateFromDto(dto, loja);
        lojaRepository.save(loja);
        return lojaMapper.toResponseDTO(loja);
    }

    @Transactional
    public void deletar(Long id) {
        lojaFinder.buscarPorId(id);
        lojaRepository.deleteById(id);
    }

    @Transactional
    public Map<String, Boolean> favoritar(Long id) {
        var loja = lojaFinder.buscarPorId(id);
        loja.setFavorito(!loja.getFavorito());
        return Map.of("favorito", loja.getFavorito());
    }
}
