package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.ServidorTemporarioDTO;
import br.gov.mt.seplag.api.dto.ServidorTemporarioRequestDTO;
import br.gov.mt.seplag.api.services.ServidorTemporarioService;
import br.gov.mt.seplag.api.specification.SpecTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "Controller de Servidor Temporário", name = "Servidor Temporário")
@RequestMapping("/servidores-temporarios")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioService service;

    @Operation(summary = "Salvar", description = "Endpoint para salvar os dados de um servidor temporário.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public ResponseEntity<ServidorTemporarioDTO> salvar(@RequestBody ServidorTemporarioRequestDTO form) {
        return new ResponseEntity<>(service.salvar(form), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar", description = "Endpoint atualizar os dados de um servidor temporário.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> atualizar(@PathVariable Long id,
                                                           @RequestBody ServidorTemporarioRequestDTO form) {
        return service.atualizar(id, form)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar", description = "Endpoint para listar aos servidores temporários.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/listar")
    public ResponseEntity<Page<ServidorTemporarioDTO>> listar(
            SpecTemplate.ServidorTemporarioSpec spec, Pageable pageable) {
        return ResponseEntity.ok(service.listar(spec, pageable));
    }

    @Operation(summary = "Buscar Por Id", description = "Endpoint para buscar um servidor temporário por id.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar", description = "Endpoint para deletar um servidor temporário.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
