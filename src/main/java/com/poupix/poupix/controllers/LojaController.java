package com.poupix.poupix.controllers;

import com.poupix.poupix.dtos.loja.LojaCreateDTO;
import com.poupix.poupix.dtos.loja.LojaResponseDTO;
import com.poupix.poupix.dtos.loja.LojaUpdateDTO;
import com.poupix.poupix.entities.Loja;
import com.poupix.poupix.services.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<LojaResponseDTO> criar(@RequestBody @Valid LojaCreateDTO dto) {
        return ResponseEntity.ok(lojaService.criar(dto));
    }

    // Listar com filtro, favorito e categoria
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Loja>> listar(
            @RequestParam(required = false) Boolean favorito,
            @RequestParam(required = false) String categoria
    ) {
        return ResponseEntity.ok(lojaService.listar(favorito, categoria));
    }

    // Buscar por id
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lojaService.buscarPorId(id));
    }

    // Atualizar
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LojaUpdateDTO dto) {
        return ResponseEntity.ok(lojaService.atualizar(id, dto));
    }

    // Favoritar
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> favoritar(@PathVariable Long id) {
        Boolean favorito = lojaService.favoritar(id);

        Map<String, Boolean> response = Map.of("favorito", favorito);
        return ResponseEntity.ok(response);
    }

    // Deletar
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lojaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
