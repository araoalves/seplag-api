package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.EnderecoFuncionalDTO;
import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.dto.ServidorEfetivoRequestDTO;
import br.gov.mt.seplag.api.services.ServidorEfetivoService;
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
@Tag(description = "Controller de Servidor Efetivo", name = "Servidor Efetivo")
@RequestMapping("/servidores-efetivos")
@RequiredArgsConstructor
public class ServidorEfetivoController {

    private final ServidorEfetivoService service;

    @Operation(summary = "Listar", description = "Endpoint para listar aos servidores efetivos.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/listar")
    public ResponseEntity<Page<ServidorEfetivoDTO>> listar(
            SpecTemplate.ServidorEfetivoSpec spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(spec, pageable));
    }

    @Operation(summary = "Salvar", description = "Endpoint para salvar os dados de um servidor efetivo.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public ResponseEntity<ServidorEfetivoDTO> salvar(@RequestBody ServidorEfetivoRequestDTO form) {
        return ResponseEntity.ok(service.salvar(form));
    }

    @Operation(summary = "Listar Servidores Por Unidade", description = "Endpoint para listar aos servidores efetivos por unidade.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/unidade/{unidId}")
    public ResponseEntity<Page<ServidorEfetivoDTO>> listarPorUnidade(
            @PathVariable Long unidId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listarPorUnidade(unidId, pageable));
    }

    @Operation(summary = "Endereço Funcional", description = "Endpoint buscar o endereço funcional de um servidor efetivo por parte do nome.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/endereco-funcional")
    public ResponseEntity<EnderecoFuncionalDTO> buscarEnderecoFuncional(@RequestParam String nome) {
        return service.buscarEnderecoFuncionalPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar", description = "Endpoint atualizar os dados de um servidor efetivo.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("/{id}")
    public ResponseEntity<ServidorEfetivoDTO> atualizarServidorEfetivo(
            @PathVariable Long id,
            @RequestBody ServidorEfetivoRequestDTO form
    ) {
        return service.atualizar(id, form)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar", description = "Endpoint para deletar um servidor efetivo.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServidorEfetivo(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
