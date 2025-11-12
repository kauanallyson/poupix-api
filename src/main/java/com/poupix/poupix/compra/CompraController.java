package com.poupix.poupix.compra;

import com.poupix.poupix.compra.dto.CompraCreateDTO;
import com.poupix.poupix.compra.dto.CompraResumoDTO;
import com.poupix.poupix.compra.dto.CompraUpdateDTO;
import com.poupix.poupix.compra.dto.RelatorioMensalDTO;
import com.poupix.poupix.compra.mapper.CompraMapper;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<CompraResumoDTO>> listar() {
        List<Compra> compras = compraService.listar();
        List<CompraResumoDTO> dtos = compraMapper.toResumoDTOList(compras);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResumoDTO> buscarPorId(@PathVariable Long id) {
        Compra compra = compraService.buscarPorId(id);
        CompraResumoDTO dto = compraMapper.toResumoDTO(compra);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> atualizar(@PathVariable Long id, @Valid @RequestBody CompraUpdateDTO dto) {
        return ResponseEntity.ok().body(compraService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Exemplo: /api/compras/relatorio/2025/11?pagamento=CREDITO
    @GetMapping("/relatorio/{ano}/{mes}")
    public ResponseEntity<RelatorioMensalDTO> relatorioMensal(
            @PathVariable int ano,
            @PathVariable int mes,
            @RequestParam(required = false) Pagamento pagamento
    ) {
        return ResponseEntity.ok(compraService.relatorioMensal(ano, mes, pagamento));
    }
}
