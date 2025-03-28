package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.UnidadeDTO;
import br.gov.mt.seplag.api.services.UnidadeService;
import br.gov.mt.seplag.api.specification.SpecTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Unidade", description = "Controller para CRUD de Unidades")
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService service;

    @Operation(summary = "Listar", description = "Lista todas as unidades com paginação e filtros.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/listar")
    public ResponseEntity<Page<UnidadeDTO>> listar(SpecTemplate.UnidadeSpec spec, Pageable pageable) {
        return ResponseEntity.ok(service.listar(spec, pageable));
    }

    @Operation(summary = "Buscar por ID", description = "Retorna os dados de uma unidade pelo seu ID.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTO> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar", description = "Cria uma nova unidade.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public ResponseEntity<UnidadeDTO> salvar(@RequestBody UnidadeDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @Operation(summary = "Atualizar", description = "Atualiza os dados de uma unidade.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeDTO> atualizar(@PathVariable Long id, @RequestBody UnidadeDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar", description = "Remove uma unidade pelo ID.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}

