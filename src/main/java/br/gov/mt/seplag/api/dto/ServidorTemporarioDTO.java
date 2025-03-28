package br.gov.mt.seplag.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorTemporarioDTO {
    private Long id;
    private String nome;
    private Integer idade;
    private String unidadeLotacao;
    private String urlFoto;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
}

