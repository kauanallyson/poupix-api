package com.poupix.poupix.controllers;

import com.poupix.poupix.dtos.compra.CompraCreateDTO;
import com.poupix.poupix.dtos.compra.CompraResumoDTO;
import com.poupix.poupix.dtos.compra.CompraUpdateDTO;
import com.poupix.poupix.dtos.compra.RelatorioMensalDTO;
import com.poupix.poupix.entities.Compra;
import com.poupix.poupix.enums.Pagamento;
import com.poupix.poupix.mappers.CompraMapper;
import com.poupix.poupix.services.CompraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService compraService;
    private final CompraMapper compraMapper;

    // Criar
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody CompraCreateDTO dto) {
        Compra compra = compraService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(compra.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<CompraResumoDTO>> listar() {
        List<Compra> compras = compraService.listar();
        List<CompraResumoDTO> dtos = compraMapper.toResumoDTOList(compras);
        return ResponseEntity.ok(dtos);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<CompraResumoDTO> buscarPorId(@PathVariable Long id) {
        Compra compra = compraService.buscarPorId(id);
        CompraResumoDTO dto = compraMapper.toResumoDTO(compra);
        return ResponseEntity.ok(dto);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<CompraResumoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CompraUpdateDTO dto
    ) {
        Compra compra = compraService.atualizar(id, dto);
        CompraResumoDTO response = compraMapper.toResumoDTO(compra);
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
        return ResponseEntity.ok(compraService.relatorioMensal(ano, mes, pagamento));
    }
}
