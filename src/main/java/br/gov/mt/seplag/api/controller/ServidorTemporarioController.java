package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.ServidorTemporarioDTO;
import br.gov.mt.seplag.api.form.ServidorTemporarioForm;
import br.gov.mt.seplag.api.services.ServidorTemporarioService;
import br.gov.mt.seplag.api.specification.SpecTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servidores-temporarios")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioService service;

    @PostMapping
    public ResponseEntity<ServidorTemporarioDTO> salvar(@ModelAttribute ServidorTemporarioForm form) {
        return new ResponseEntity<>(service.salvar(form), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> atualizar(@PathVariable Long id,
                                                           @ModelAttribute ServidorTemporarioForm form) {
        return service.atualizar(id, form)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ServidorTemporarioDTO>> listar(
            SpecTemplate.ServidorTemporarioSpec spec, Pageable pageable) {
        return ResponseEntity.ok(service.listar(spec, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
