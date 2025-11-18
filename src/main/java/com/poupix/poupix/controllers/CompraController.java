package com.poupix.poupix.controllers;

import com.poupix.poupix.dtos.compra.CompraCreate;
import com.poupix.poupix.dtos.compra.CompraResponse;
import com.poupix.poupix.dtos.compra.CompraUpdate;
import com.poupix.poupix.dtos.compra.RelatorioMensal;
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
    public ResponseEntity<Void> criar(@Valid @RequestBody CompraCreate request) {
        compraService.criar(request);
        return ResponseEntity.ok().build();
    }

    // Listar com filtros
    @GetMapping
    public ResponseEntity<List<CompraResponse>> listarComFiltro(
            @RequestParam(required = false) @Min(2000) @Max(2100) int ano,
            @RequestParam(required = false) @Min(1) @Max(12) int mes,
            @RequestParam(required = false) Pagamento pagamento
    ) {
        var response = compraService.listarComFiltros(ano, mes, pagamento);
        return ResponseEntity.ok(response);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> buscarPorId(@PathVariable Long id) {
        var response = compraService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<CompraResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CompraUpdate request
    ) {
        var response = compraService.atualizar(id, request);
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
    public ResponseEntity<RelatorioMensal> relatorioMensal(
            @PathVariable @Min(2000) @Max(2100) int ano,
            @PathVariable @Min(1) @Max(12) int mes,
            @RequestParam(required = false) Pagamento pagamento
    ) {
        var response = compraService.relatorioMensal(ano, mes, pagamento);
        return ResponseEntity.ok(response);
    }
}
