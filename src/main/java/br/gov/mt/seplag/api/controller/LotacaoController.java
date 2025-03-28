package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.LotacaoDTO;
import br.gov.mt.seplag.api.services.LotacaoService;
import br.gov.mt.seplag.api.specification.SpecTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lotacoes")
@RequiredArgsConstructor
@Tag(name = "Lotação", description = "Controller para CRUD de lotações de servidores")
public class LotacaoController {

    private final LotacaoService service;

    @Operation(summary = "Listar", description = "Lista todas as lotações com paginação.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/listar")
    public ResponseEntity<Page<LotacaoDTO>> listar(SpecTemplate.LotacaoSpec spec, Pageable pageable) {
        return ResponseEntity.ok(service.listar(spec, pageable));
    }

    @Operation(summary = "Buscar por ID", description = "Busca uma lotação pelo ID.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/{id}")
    public ResponseEntity<LotacaoDTO> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar", description = "Cria uma nova lotação.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public ResponseEntity<LotacaoDTO> salvar(@RequestBody LotacaoDTO dto) {
        return new ResponseEntity<>(service.salvar(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar", description = "Atualiza os dados de uma lotação.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("/{id}")
    public ResponseEntity<LotacaoDTO> atualizar(@PathVariable Long id, @RequestBody LotacaoDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar", description = "Remove uma lotação pelo ID.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
