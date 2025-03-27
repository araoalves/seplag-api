package br.gov.mt.seplag.api.controller;

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
}
