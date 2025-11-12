package com.poupix.poupix.loja;

import com.poupix.poupix.loja.dto.LojaCreateDTO;
import com.poupix.poupix.loja.dto.LojaResponseDTO;
import com.poupix.poupix.loja.dto.LojaUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lojas")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;

    @PostMapping
    public ResponseEntity<LojaResponseDTO> criar(@RequestBody @Valid LojaCreateDTO dto) {
        return ResponseEntity.ok(lojaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<LojaResponseDTO>> listarTodas(
            @RequestParam(required = false) Boolean favorito,
            @RequestParam(required = false) String categoria
    ) {
        return ResponseEntity.ok(lojaService.listarTodas(favorito, categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lojaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid LojaUpdateDTO dto) {
        return ResponseEntity.ok(lojaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lojaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
