package com.poupix.poupix.controllers;

import com.poupix.poupix.dtos.loja.LojaCreateDTO;
import com.poupix.poupix.dtos.loja.LojaResponseDTO;
import com.poupix.poupix.dtos.loja.LojaUpdateDTO;
import com.poupix.poupix.services.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lojas")
@RequiredArgsConstructor
public class LojaController {
    private final LojaService lojaService;

    // Criar
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody LojaCreateDTO dto) {
        lojaService.criar(dto);
        return ResponseEntity.ok().build();
    }

    // Listar com filtro
    @GetMapping
    public ResponseEntity<List<LojaResponseDTO>> listarComFiltro(
            @RequestParam(required = false) Boolean favorito,
            @RequestParam(required = false) String categoria
    ) {
        var response = lojaService.listarComFiltros(favorito, categoria);
        return ResponseEntity.ok(response);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable Long id) {
        var response = lojaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LojaUpdateDTO dto) {
        var response = lojaService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lojaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Favoritar
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> favoritar(@PathVariable Long id) {
        var response  = lojaService.favoritar(id);
        return ResponseEntity.ok(response);
    }
}
