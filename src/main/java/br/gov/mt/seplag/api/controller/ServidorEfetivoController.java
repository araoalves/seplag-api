package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.EnderecoFuncionalDTO;
import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.form.ServidorEfetivoForm;
import br.gov.mt.seplag.api.services.ServidorEfetivoService;
import br.gov.mt.seplag.api.specification.SpecTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/servidores-efetivos")
@RequiredArgsConstructor
public class ServidorEfetivoController {

    private final ServidorEfetivoService service;

    @GetMapping("/listar")
    public ResponseEntity<Page<ServidorEfetivoDTO>> listar(
            SpecTemplate.ServidorEfetivoSpec spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(spec, pageable));
    }

    @PostMapping
    public ResponseEntity<ServidorEfetivoDTO> salvar(@ModelAttribute ServidorEfetivoForm form) {
        return ResponseEntity.ok(service.salvar(form));
    }

    @GetMapping("/unidade/{unidId}")
    public ResponseEntity<Page<ServidorEfetivoDTO>> listarPorUnidade(
            @PathVariable Long unidId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listarPorUnidade(unidId, pageable));
    }

    @GetMapping("/endereco-funcional")
    public ResponseEntity<EnderecoFuncionalDTO> buscarEnderecoFuncional(@RequestParam String nome) {
        return service.buscarEnderecoFuncionalPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServidorEfetivoDTO> atualizarServidorEfetivo(
            @PathVariable Long id,
            @ModelAttribute ServidorEfetivoForm form
    ) {
        return service.atualizar(id, form)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServidorEfetivo(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
