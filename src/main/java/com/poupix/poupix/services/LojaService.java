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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LojaService {

    private final LojaRepository lojaRepository;
    private final LojaMapper mapper = new LojaMapper();

    public LojaResponseDTO criar(LojaCreateDTO dto) {
        Loja loja = mapper.toEntity(dto);
        return mapper.toResponseDTO(lojaRepository.save(loja));
    }

    public List<LojaResponseDTO> listarTodas(Boolean favorito, String categoria) {
        boolean filtrarFavorito = Boolean.TRUE.equals(favorito);
        boolean filtrarCategoria = categoria != null && !categoria.isBlank();

        List<Loja> lojas = switch ((filtrarFavorito ? 1 : 0) + (filtrarCategoria ? 2 : 0)) {
            case 3 -> lojaRepository.findByFavoritoTrueAndCategoriaIgnoreCase(categoria);
            case 2 -> lojaRepository.findByCategoriaIgnoreCase(categoria);
            case 1 -> lojaRepository.findByFavoritoTrue();
            default -> lojaRepository.findAll();
        };

        return mapper.toResponseDTOList(lojas);
    }

    public LojaResponseDTO buscarPorId(Long id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + id + " não encontrada"));
        return mapper.toResponseDTO(loja);
    }

    public LojaResponseDTO atualizar(Long id, LojaUpdateDTO dto) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com id " + id + " não encontrada"));
        mapper.updateFromDto(dto, loja);
        return mapper.toResponseDTO(lojaRepository.save(loja));
    }

    public void deletar(Long id) {
        if (!lojaRepository.existsById(id))
            throw new ResourceNotFoundException("Loja com id " + id + " não encontrada");
        lojaRepository.deleteById(id);
    }
}
