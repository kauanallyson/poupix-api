package com.poupix.poupix.controllers;

import com.poupix.poupix.dtos.compra.CompraCreateDTO;
import com.poupix.poupix.dtos.compra.CompraResponseDTO;
import com.poupix.poupix.dtos.compra.CompraUpdateDTO;
import com.poupix.poupix.dtos.compra.RelatorioMensalDTO;
import com.poupix.poupix.enums.Pagamento;
import com.poupix.poupix.services.CompraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService compraService;

    // Criar
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody CompraCreateDTO dto) {
        compraService.criar(dto);
        return ResponseEntity.ok().build();
    }

    // Listar com filtros
    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarComFiltro(
            @RequestParam(required = false) @Min(2000) @Max(2100) int ano,
            @RequestParam(required = false) @Min(1) @Max(12) int mes,
            @RequestParam(required = false) Pagamento pagamento
    ) {
        var response = compraService.listarComFiltros(ano, mes, pagamento);
        return ResponseEntity.ok(response);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> buscarPorId(@PathVariable Long id) {
        var response = compraService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CompraUpdateDTO dto
    ) {
        var response = compraService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Relatorio por mes com filtro
    @GetMapping("/relatorio/{ano}/{mes}")
    public ResponseEntity<RelatorioMensalDTO> relatorioMensal(
            @PathVariable @Min(2000) @Max(2100) int ano,
            @PathVariable @Min(1) @Max(12) int mes,
            @RequestParam(required = false) Pagamento pagamento
    ) {
        var response = compraService.relatorioMensal(ano, mes, pagamento);
        return ResponseEntity.ok(response);
    }
}
