package br.gov.mt.seplag.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LotacaoDTO {
    private Long lotId;
    private Long pessoaId;
    private Long unidadeId;
    private LocalDate lotDataLotacao;
    private LocalDate lotDataRemocao;
    private String lotPortaria;
}
